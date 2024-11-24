package com.onetwo.mongddang.domain.record.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.s3.S3ImageService;
import com.onetwo.mongddang.common.s3.errors.CustomS3ErrorCode;
import com.onetwo.mongddang.common.utils.JsonUtils;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.record.errors.CustomRecordErrorCode;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.vital.application.VitalUtils;
import com.onetwo.mongddang.domain.vital.dto.ResponseDailyGlucoseDto;
import com.onetwo.mongddang.domain.vital.service.VitalService;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.meal;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordMealService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;
    private final JsonUtils jsonUtils;
    private final MissionLogUtils missionLogUtils;
    private final GameLogUtils gameLogUtils;
    private final VitalUtils vitalUtils;
    private final VitalService vitalService;


    /**
     * 식사 시작하기
     *
     * @param childId     식사 시작을 시도하는 어린이의 아이디
     * @param contentJson 식사 내용
     * @param imageFile   이미지 파일 (파일 경로 또는 URL)
     * @param mealTime    식사 시간 (enum으로 처리)
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto startMeal(Long childId, String contentJson, String imageFile, String mealTime) {
        log.info("startMeal childId: {}", childId);

        // JSON 형식이 리스트 형식임을 검사
        try {
            jsonUtils.checkJsonTypeList(contentJson);
        } catch (Exception e) {
            throw new RestApiException(CustomRecordErrorCode.BAD_INGREDIENT_INPUT);
        }
        // SON 문자열을 JsonNode 로 변환
        JsonNode content = jsonUtils.JsonStringToJsonNode(contentJson);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 진행 중인 활동 기록 조회
        log.info("이미 시작된 식사 기록 확인 (in english : Check if the meal record has already started)");
        Optional<Record> existingMealRecord = recordRepository.findTopByChildAndEndTimeIsNullOrderByIdDesc(child);
        if (existingMealRecord.isPresent()) {
            throw new RestApiException(CustomRecordErrorCode.EXISTING_ONGOING_RECORD);
        }


        String imageUrl = null;
//        if (imageFile != null) {
//            // 이미지 파일을 S3에 업로드
//            log.info("식사 이미지 파일을 S3에 업로드 시도 (in english : Try to upload meal image file to S3)");
//            try {
//                imageUrl = s3ImageService.upload(imageFile); // MultipartFile을 File로 변환 후 S3에 업로드
//            } catch (Exception e) {
//                throw new RestApiException(CustomS3ErrorCode.IMAGE_UPLOAD_FAILED);
//            }
//            log.info("식사 이미지 파일을 S3에 업로드 완료 (in english : Meal image file uploaded to S3)");
//        }

        log.info(content.toString());

        // 식사 시작 시간 기록
        Record mealRecord = Record.builder()
                .child(child)
                .category(meal)
                .startTime(LocalDateTime.now())
                .endTime(null)
                .content(content)
                .imageUrl(imageUrl)
                .isDone(false)
                .mealTime(Record.MealTimeType.valueOf(mealTime))
                .build();

        // 게임 로그 업데이트
        gameLogUtils.addGameLog(child, GameLog.GameLogCategory.meal_count);

        recordRepository.save(mealRecord);
        log.info("식사 시작 기록 완료. 시작시간 : {}", mealRecord.getStartTime());

        // 식사 시간에 따른 미션 업데이트
        switch (mealRecord.getMealTime()) {
            case breakfast:
                missionLogUtils.completeMission(child, MissionDto.Mission.breakfast);
                break;
            case lunch:
                missionLogUtils.completeMission(child, MissionDto.Mission.lunch);
                break;
            case dinner:
                missionLogUtils.completeMission(child, MissionDto.Mission.dinner);
                break;
        }

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", bloodSugarLevel);

        return ResponseDto.builder()
                .message("식사를 시작합니다.")
                .data(data)
                .build();
    }

    /**
     * 식사 종료하기
     *
     * @param childId 식사 종료를 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto endMeal(Long childId) {
        log.info("endMeal childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        log.info("가장 최근에 시작된 식사 기록 조회");
        Optional<Record> lastedMealRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, meal);

        log.info("이미 시작된 식사 기록 확인");
        if (lastedMealRecord.isEmpty()) {
            throw new RestApiException(CustomRecordErrorCode.MEAL_NOT_STARTED);
        }

        // 식사 종료 시간 기록
        Record mealRecord = lastedMealRecord.get();
        mealRecord.setEndTime(LocalDateTime.now());
        mealRecord.setIsDone(true);

        recordRepository.save(mealRecord);
        log.info("식사 종료 기록 완료. 종료시간 : {}", mealRecord.getEndTime());

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", bloodSugarLevel);

        return ResponseDto.builder()
                .message("식사를 종료합니다.")
                .data(data)
                .build();
    }


    // 식사 수정하기
    @Transactional
    public ResponseDto editMeal(Long childId, Long recordId, String contentJson, MultipartFile imageFile, String mealTime) {
        log.info("updateMeal childId: {}, recordId: {}", childId, recordId);

        // SON 문자열을 JsonNode로 변환
        JsonNode content = jsonUtils.JsonStringToJsonNode(contentJson);

        // 어린이 정보 조회
        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 식사 기록 조회
        Record mealRecord = recordRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(CustomRecordErrorCode.MEAL_RECORD_NOT_FOUND));
        log.info("mealRecord: {}", mealRecord.getId());

        // 식사 기록이 어린이의 기록인지 확인
        log.info("식사 기록이 어린이의 기록인지 확인 (in english : Check if the meal record is the child's record)");
        if (!Objects.equals(mealRecord.getChild().getId(), childId)) {
            throw new RestApiException(CustomRecordErrorCode.CHILD_ACCESS_DENIED);
        }

        String imageUrl = null;

        log.info("식사 이미지 파일이 있는지 확인 (in english : Check if there is a meal image file)");
        // 이미지 파일이 있다면 s3 등록 진행
        if (imageFile != null && !imageFile.isEmpty()) {
            // 이미지 파일을 S3에 업로드
            log.info("식사 이미지 파일을 S3에 업로드 시도 (in english : Try to upload meal image file to S3)");
            try {
                imageUrl = s3ImageService.upload(imageFile); // MultipartFile 을 File 로 변환 후 S3에 업로드
            } catch (Exception e) {
                throw new RestApiException(CustomS3ErrorCode.IMAGE_UPLOAD_FAILED);
            }
            log.info("식사 이미지 파일을 S3에 업로드 완료 (in english : Meal image file uploaded to S3)");
        }

        log.info("식사 이미지 파일 없음 (in english : No meal image file)");

        // 식사 내용 수정
        mealRecord.setContent(content);
        mealRecord.setImageUrl(imageUrl);
        mealRecord.setMealTime(Record.MealTimeType.valueOf(mealTime));

        recordRepository.save(mealRecord);
        log.info("식사 수정 완료 (in english : Meal modification completed)");

        return ResponseDto.builder()
                .message("식사를 수정합니다.")
                .build();
    }


}
