package com.onetwo.mongddang.domain.vital.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.utils.GptUtils;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.application.CtoPUtils;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.vital.application.VitalUtils;
import com.onetwo.mongddang.domain.vital.dto.*;
import com.onetwo.mongddang.domain.vital.errors.CustomVitalErrorCode;
import com.onetwo.mongddang.domain.vital.model.BloodGlucose;
import com.onetwo.mongddang.domain.vital.model.Vital;
import com.onetwo.mongddang.domain.vital.repository.BloodGlucoseRepository;
import com.onetwo.mongddang.domain.vital.repository.VitalRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class VitalService {

    private final VitalRepository vitalRepository;
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final CtoPUtils ctoPUtils;
    private final VitalUtils vitalUtils;
    private final GptUtils gptUtils;
    private final BloodGlucoseRepository bloodGlucoseRepository;


    /**
     * 혈당 기록 조회
     *
     * @param userId   유저 아이디
     * @param nickname 닉네임
     * @param date     날짜
     * @return
     */
    public ResponseDto getBloodSugar(Long userId, String nickname, LocalDate date) {
        log.info("getBloodSugar userId: {}, nickname: {}", userId, nickname);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 조회 권한 확인
        // ctoPUtils.validateProtectorAccessChildData(user, child);

        // 해당 날짜의 혈당 기록 조회
        List<Vital> todayVital = vitalRepository.findByChildAndMeasurementTimeBetween(child, date.atStartOfDay(), date.atTime(23, 59, 59));

        // responseDTO 로 변환
        List<ResponseDailyGlucoseDto> responseDailyGlucoseDto = new ArrayList<>();
        for (Vital vital : todayVital) {
            responseDailyGlucoseDto.add(ResponseDailyGlucoseDto.builder()
                    .id(vital.getId())
                    .bloodSugarLevel(vital.getBloodSugarLevel())
                    .measurementTime(vital.getMeasurementTime())
                    .content(null)
                    .status(ResponseDailyGlucoseDto.Status.valueOf(vital.getStatus().name()))
                    .notification(vital.getIsNotification())
                    .build());
        }

        return ResponseDto.builder()
                .message("일일 혈당 기록 조회 성공")
                .data(responseDailyGlucoseDto)
                .build();
    }


    @Transactional
    public ResponseDto getCurrentBloodSugar(Long userId, String nickname) {
        log.info("getCurrentBloodSugar userId: {}, nickname: {}", userId, nickname);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 조회 권한 확인
        // ctoPUtils.validateProtectorAccessChildData(user, child);


        log.info("child: {}", child.getEmail());
        Vital vital = vitalRepository.findTopByChildOrderByIdDesc(child).orElse(null);

        // 측정 시간과 현재 시간의 차이를 계산, 1분 이내에 조회한 경우라면 동일한 결과 반환
        LocalDateTime measurementTime = vital.getMeasurementTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(measurementTime, currentTime);
        log.info("measurementTime: {}", measurementTime);
        log.info("currentTime: {}", currentTime);
        log.info("duration: {}", duration.toMinutes());
        if (duration.toMinutes() < 1) {
            log.info("동일한 결과 반환 (There's a vital that passed time is less than 1min.");
            // 1분이내 => 동일한 결과 반환
            ResponseDailyGlucoseDto responseDailyGlucoseDto = ResponseDailyGlucoseDto.builder()
                    .id(vital.getId())
                    .bloodSugarLevel(vital.getBloodSugarLevel())
                    .measurementTime(vital.getMeasurementTime())
                    .content(null)
                    .status(ResponseDailyGlucoseDto.Status.valueOf(vital.getStatus().name()))
                    .notification(vital.getIsNotification())
                    .build();

            return ResponseDto.builder()
                    .message("현재 혈당 조회 성공")
                    .data(responseDailyGlucoseDto)
                    .build();

        } else {
            // 1분 이내의 기록이 없는 경우

            // 최근 30분 이내의 기록 조회
            Record recentRecord = recordRepository.findTopByChildAndEndTimeIsBetweenOrderByIdDesc(child, LocalDateTime.now().minusMinutes(30), LocalDateTime.now()).orElse(null);

            // 가중치 계산
            int weight = 0;
            if (recentRecord != null) {
                if (recentRecord.getCategory().equals(Record.RecordCategoryType.exercise)) {
                    weight = -5;
                    log.info("운동 가중치 exercise weight: {}", weight);
                } else if (recentRecord.getCategory().equals(Record.RecordCategoryType.meal
                )) {
                    weight = 5;
                    log.info("식사 가중치 meal weight: {}", weight);
                } else if (recentRecord.getCategory().equals(Record.RecordCategoryType.medication)) {
                    weight = -10;
                    log.info("복약 가중치 medication weight : {}", weight);
                }
            }

            if (vital == null) {
                // 현재 혈당이 없는 경우 랜덤으로 생성
                log.info("새로운 결과 생성 (Created new vital data.");
                Random random = new Random();
                int bloodSugarLevel = random.nextInt(31) + 90; // 90부터 120까지의 랜덤 수치 생성

                vital = Vital.builder()
                        .child(child)
                        .bloodSugarLevel(bloodSugarLevel + weight)
                        .measurementTime(LocalDateTime.now()) // 필요한 경우 현재 시간으로 설정 가능
                        .content(null)
                        .status(Vital.GlucoseStatusType.normal)
                        .isNotification(false)
                        .build();
            } else {
                // 혈당이 있는 경우 보정치 반영
                log.info("보정치 반영 (reflect a vital with correctionValue.");
                int curBloodSugarLevel = vital.getBloodSugarLevel();

                Random random = new Random();

                int correctionValue;
                if (curBloodSugarLevel > 300) {
                    // 수치가 매우 높은 경우 -15~5
                    correctionValue = curBloodSugarLevel + random.nextInt(20) - 15;
                } else if (curBloodSugarLevel > 200) {
                    // 수치가 높은 경우 -10~5
                    correctionValue = curBloodSugarLevel + random.nextInt(16) - 10;
                } else if (curBloodSugarLevel < 90) {
                    // 수치가 낮은 경우 -5~15
                    correctionValue = curBloodSugarLevel + random.nextInt(21) - 5;
                } else if (curBloodSugarLevel < 60) {
                    // 수치가 극도로 낮은 경우 5~20
                    correctionValue = curBloodSugarLevel + random.nextInt(15) + 5;
                } else {
                    // 일반적인 상황에서는 +-10 범위의 랜덤 수치 생성
                    correctionValue = curBloodSugarLevel + random.nextInt(21) - 10;
                }

                // 반환할 상태값 설정
                Vital.GlucoseStatusType status;
                if (correctionValue < 70) {
                    status = Vital.GlucoseStatusType.low;
                } else if (correctionValue > 200) {
                    status = Vital.GlucoseStatusType.high;
                } else {
                    status = Vital.GlucoseStatusType.normal;
                }

                log.info("bloodSugarLevel: {}", vital.getBloodSugarLevel());
                vital = Vital.builder()
                        .child(child)
                        .bloodSugarLevel(correctionValue + weight)
                        .measurementTime(LocalDateTime.now()) // 필요한 경우 현재 시간으로 설정 가능
                        .content(null)
                        .status(status)
                        .isNotification(false)
                        .build();
            }
        }

        // 저장
        vitalRepository.save(vital);

        // responseDTO 로 변환
        ResponseDailyGlucoseDto responseDailyGlucoseDto = ResponseDailyGlucoseDto.builder()
                .id(vital.getId())
                .bloodSugarLevel(vital.getBloodSugarLevel())
                .measurementTime(vital.getMeasurementTime())
                .content(null)
                .status(ResponseDailyGlucoseDto.Status.valueOf(vital.getStatus().name()))
                .notification(vital.getIsNotification())
                .build();

        return ResponseDto.builder()
                .message("현재 혈당 조회 성공")
                .data(responseDailyGlucoseDto)
                .build();
    }

    /**
     * 리포트 조회
     *
     * @param userId    유저 아이디
     * @param nickname  닉네임
     * @param startDate 시작 날짜
     * @param endDate   종료 날짜
     * @return
     */
    public ResponseDto getReport(Long userId, String nickname, LocalDate startDate, LocalDate endDate) {
        log.info("getReport userId: {}, nickname: {}, startDate: {}, endDate: {}", userId, nickname, startDate, endDate);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 조회 권한 확인
        // ctoPUtils.validateProtectorAccessChildData(user, child);

        // 해당 날짜의 혈당 기록 조회
        List<Vital> vitalList = vitalRepository.findByChildAndMeasurementTimeBetween(child, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // 리포트 계산
        float abg = vitalUtils.getAverageGlucose(vitalList); // 평균 혈당
        float gmi = vitalUtils.getGMI(abg); // 혈당 관리 지표
        float cv = vitalUtils.getGlucoseVariability(vitalList, abg); // 혈당 변동성
        float tir = vitalUtils.getInTargetRangeRatio(vitalList); // 목표 범위 내 비율

        // 혈당 측정 시간대별로 정리
        List<GlucoseMeasurementTimeDto> glucoseMeasurementTimeList = new ArrayList<>();
        for (Vital vital : vitalList) {
            glucoseMeasurementTimeList.add(GlucoseMeasurementTimeDto.builder()
                    .measurementTime(vital.getMeasurementTime())
                    .bloodSugarLevel(vital.getBloodSugarLevel())
                    .build());
        }

        // responseDTO 로 변환
        ResponseBloodSugarReportDto responseBloodSugarReportDto = ResponseBloodSugarReportDto.builder()
                .glucoseMeasurementItmeList(glucoseMeasurementTimeList)
                .gmi(gmi)
                .abg(abg)
                .cv(cv)
                .tir(tir)
                .build();

        return ResponseDto.builder()
                .message("리포트 조회 성공")
                .data(responseBloodSugarReportDto)
                .build();
    }


    public ResponseDto getGptSummary(Long userId, String nickanme, String message) {
        log.info("getGptSummary");

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
            log.info("Existing User : {}", userId);

            String prompt = """                        
                        지금 제공한 정보는 1형 당뇨에 걸린 소아의 일주일 간 평균적인 혈당 관리에 대한 정보입니다. gmi(%)란 당화혈색소평균 수치이고 abg란 평균 혈당 수치이며 cv(%)란 혈당 변동성 수치이고 tir(%)란 목표 범위 내 비율입니다. 이 수치 정보를 통해 이번주 소아의 혈당 관리 상태에 대해 종합적인 평가를 내려주세요. 현재 상태에 대해 앞으로 혈당 관리를 어떻게 하면 좋을지도 1줄 정도로 함께 제안해주세요. 소아라는 언급은 필요하지 않습니다. 문자열 강조나 줄바꿈은 절대로 필요하지 않습니다. 8-10세 어린이에게 말하듯 해요체를 사용한 부드러운 존댓말을 사용해주세요. 마지막에 혈당 관리 팁을 하나 제시하고, 잘 하고 있으니 앞으로도 힘내자는 식의 말로 사랑스럽게 마무리해주세요.
                        
                        (참고 기준을 바탕으로 분석할 것)
                        gmi기준:(5.7미만:정상, 5.7~9.0:보통, 8.0~11.0:주의, 11초과: 위험) abg기준:(70~180:정상, 180~250:보통, 250~300:주의, 300초과: 위험), cv기준:(<36:정상, 36~49:보통, 50~59:주의, 60초과: 위험), tir기준:(>70:정상, 50~70:보통, 30~50:주의, <30: 위험)
                    """;

            String messageWithPrompt = message + prompt;

            // GPT 요약 생성
            String summary = gptUtils.requestGpt(messageWithPrompt);

            return ResponseDto.builder()
                    .message("GPT 요약 생성 성공")
                    .data(summary)
                    .build();

        } catch (Exception e) {
            log.error("Error occurred while getting GPT summary", e);
            throw new RestApiException(CustomVitalErrorCode.VITAL_GENERATE_GPT_FAILED);
        }
    }

    public ResponseDto getSamsung(Long userId, BloodGlucoseRequest dto) {
        try {
            // 사용자 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
            log.info("Existing User : {}", userId);
            Vital.GlucoseStatusType bloodStatus = Vital.GlucoseStatusType.normal; // 기본값 설정

            if (dto.getBloodSugarLevel() != null) {
                // 상태 계산 로직
                if (dto.getBloodSugarLevel() < 70) {
                    System.out.println("Status: Low");
                    bloodStatus = Vital.GlucoseStatusType.normal;
                } else if (dto.getBloodSugarLevel() > 140) {
                    System.out.println("Status: High");
                    bloodStatus = Vital.GlucoseStatusType.high;
                } else {
                    System.out.println("Status: Normal");
                    bloodStatus = Vital.GlucoseStatusType.normal;
                }
            }

            // BloodGlucose 생성 및 저장
            Vital vital = Vital.builder()
                    .child(user)
                    .measurementTime(dto.getMeasurementTime())
                    .bloodSugarLevel(dto.getBloodSugarLevel())
                    .content(null) // content는 필요하면 추가로 처리
                    .isNotification(false)
                    .status(bloodStatus) // Enum 변환 로
                    .packageName(dto.getPackageName())
                    .build();

            Vital newBg = vitalRepository.save(vital);

            // Response DTO 생성
            BloodGlucoseResponse responsedto = BloodGlucoseResponse.builder()
                    .id(newBg.getId())
                    .bloodSugarLevel(newBg.getBloodSugarLevel())
                    .status(newBg.getStatus())// 이미 Entity 상태와 DTO 상태가 동일한 Enum 타입
                    .content(null)
                    .measurementTime(newBg.getMeasurementTime())
                    .notification(newBg.getIsNotification())
                    .packageName(newBg.getPackageName())
                    .build();

            // 최종 Response 반환
            return ResponseDto.builder()
                    .message("삼성헬스 등록 성공")
                    .data(responsedto)
                    .build();
        } catch (RestApiException e) {
            log.error("Error during Samsung health registration: {}", e.getMessage());
            throw e;
        }
    }

    private Vital.GlucoseStatusType mapStatus(ResponseDailyGlucoseDto.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        // Status Enum에서 GlucoseStatusType Enum으로 변환
        switch (status) {
            case low:
                return Vital.GlucoseStatusType.low;
            case normal:
                return Vital.GlucoseStatusType.normal;
            case high:
                return Vital.GlucoseStatusType.high;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

}
