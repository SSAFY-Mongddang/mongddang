package com.onetwo.mongddang.domain.record.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
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
import com.onetwo.mongddang.domain.vital.dto.ResponseDailyGlucoseDto;
import com.onetwo.mongddang.domain.vital.service.VitalService;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.exercise;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordExerciseService {


    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final MissionLogUtils missionLogUtils;
    private final GameLogUtils gameLogUtils;
    private final VitalService vitalService;


    /**
     * 운동 시작하기
     *
     * @param childId 운동 시작을 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto startExercise(Long childId) {
        log.info("startExercise childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 진행 중인 활동 기록 조회
        log.info("이미 시작된 운동 기록 확인 (In English: Check if there is already a started exercise record)");
        Optional<Record> existingRecord = recordRepository.findTopByChildAndEndTimeIsNullOrderByIdDesc(child);
        if (existingRecord.isPresent()) {
            throw new RestApiException(CustomRecordErrorCode.EXISTING_ONGOING_RECORD);
        }

        // 운동 시작 시간 기록
        Record exerciseRecord = Record.builder()
                .child(child)
                .category(exercise)
                .startTime(LocalDateTime.now())
                .endTime(null)
                .content(null)
                .imageUrl(null)
                .isDone(false)
                .mealTime(null)
                .build();

        recordRepository.save(exerciseRecord);

        log.info("운동 시작 기록 완료. 시작시간 : {}", exerciseRecord.getStartTime());
        // 미션 업데이트
        missionLogUtils.completeMission(child, MissionDto.Mission.exercise);

        log.info("미션 업데이트 완료");
        // 게임 로그 업데이트
        gameLogUtils.addGameLog(child, GameLog.GameLogCategory.exercise_count);
        log.info("게임 로그 업데이트 완료");
        
        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", bloodSugarLevel);

        log.info("운동 시작 완료");
        return ResponseDto.builder()
                .message("운동을 시작합니다.")
                .data(data)
                .build();

    }

    /**
     * 운동 종료하기
     *
     * @param childId 운동 종료를 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto endExercise(Long childId) {
        log.info("endExercise childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 가장 최근에 시작된 운동 기록 조회
        Optional<Record> lastedExerciseRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, exercise);
        log.info("가장 최근에 시작된 운동 기록 조회 성공");

        // 운동 중인지 확인
        log.info("이미 시작된 운동 기록 확인");
        if (lastedExerciseRecord.isEmpty()) {
            throw new RestApiException(CustomRecordErrorCode.EXERCISE_NOT_STARTED);
        }

        // 운동 종료 시간 기록
        Record exerciseRecord = lastedExerciseRecord.get();
        exerciseRecord.setEndTime(LocalDateTime.now());
        exerciseRecord.setIsDone(true);

        recordRepository.save(exerciseRecord);
        log.info("운동 종료 기록 완료. 종료시간 : {}", exerciseRecord.getEndTime());

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", responseDailyGlucoseDto.getBloodSugarLevel());


        return ResponseDto.builder()
                .message("운동을 종료합니다.")
                .data(data)
                .build();
    }


}
