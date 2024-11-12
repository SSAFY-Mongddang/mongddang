package com.onetwo.mongddang.domain.vital.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.application.CtoPUtils;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.vital.application.VitalUtils;
import com.onetwo.mongddang.domain.vital.dto.GlucoseMeasurementTimeDto;
import com.onetwo.mongddang.domain.vital.dto.ResponseBloodSugarReportDto;
import com.onetwo.mongddang.domain.vital.dto.ResponseDailyGlucoseDto;
import com.onetwo.mongddang.domain.vital.model.Vital;
import com.onetwo.mongddang.domain.vital.repository.VitalRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CtoPUtils ctoPUtils;
    private final VitalUtils vitalUtils;


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
//        ctoPUtils.validateProtectorAccessChildData(user, child);

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
//        ctoPUtils.validateProtectorAccessChildData(user, child);

        log.info("child: {}", child.getEmail());
        Vital vital = vitalRepository.findTopByChildOrderById(child).orElse(null);

        // 현재 혈당이 없는 경우 랜덤으로 생성
        if (vital == null) {
            Random random = new Random();
            int bloodSugarLevel = random.nextInt(31) + 90; // 90부터 120까지의 랜덤 수치 생성

            vital = Vital.builder()
                    .child(child)
                    .bloodSugarLevel(bloodSugarLevel)
                    .measurementTime(LocalDateTime.now()) // 필요한 경우 현재 시간으로 설정 가능
                    .content(null)
                    .status(Vital.GlucoseStatusType.normal)
                    .isNotification(false)
                    .build();

        } else {
            // 현재 혈당이 있는 경우 +-10 범위의 랜덤 수치 생성
            Random random = new Random();
            int randomNumber = random.nextInt(21) - 10; // 0부터 20까지의 랜덤 수치에 -10을 더함
            log.info(randomNumber + "");

            Vital.GlucoseStatusType status;

            if (vital.getBloodSugarLevel() + randomNumber < 70) {
                status = Vital.GlucoseStatusType.low;
            } else if (vital.getBloodSugarLevel() + randomNumber > 200) {
                status = Vital.GlucoseStatusType.high;
            } else {
                status = Vital.GlucoseStatusType.normal;
            }

            log.info("bloodSugarLevel: {}", vital.getBloodSugarLevel());
            vital = Vital.builder()
                    .child(child)
                    .bloodSugarLevel(vital.getBloodSugarLevel() + randomNumber)
                    .measurementTime(LocalDateTime.now()) // 필요한 경우 현재 시간으로 설정 가능
                    .content(null)
                    .status(status)
                    .isNotification(false)
                    .build();
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
//        ctoPUtils.validateProtectorAccessChildData(user, child);

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
                .gptSummary("정상")
                .build();

        return ResponseDto.builder()
                .message("리포트 조회 성공")
                .data(responseBloodSugarReportDto)
                .build();
    }


}
