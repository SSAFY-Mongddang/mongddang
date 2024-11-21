package com.onetwo.mongddang.domain.record.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
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

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.sleeping;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSleepingService {


    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final GameLogUtils gameLogUtils;
    private final VitalService vitalService;


    /**
     * 수면 시작하기
     *
     * @param childId 수면 시작을 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto startSleep(Long childId) {
        log.info("startSleep childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 진행 중인 수면 기록 조회
        log.info("이미 시작된 수면 기록 확인");
        Optional<Record> existingSleepRecord = recordRepository.findTopByChildAndEndTimeIsNullOrderByIdDesc(child);
        if (existingSleepRecord.isPresent()) {
            throw new RestApiException(CustomRecordErrorCode.EXISTING_ONGOING_RECORD);
        }

        // 수면 시작 시간 기록
        Record sleepRecord = Record.builder()
                .child(child)
                .category(sleeping)
                .startTime(LocalDateTime.now())
                .endTime(null)
                .content(null)
                .imageUrl(null)
                .isDone(false)
                .mealTime(null)
                .build();

        recordRepository.save(sleepRecord);
        log.info("수면 시작 기록 완료. 시작시간 : {}", sleepRecord.getStartTime());

        // 게임 로그 업데이트
        gameLogUtils.addGameLog(child, GameLog.GameLogCategory.sleeping_count);

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", bloodSugarLevel);

        return ResponseDto.builder()
                .message("수면을 시작합니다.")
                .data(bloodSugarLevel)
                .build();
    }

    /**
     * 수면 종료하기
     *
     * @param childId 수면 종료를 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto endSleep(Long childId) {
        log.info("endSleep childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        log.info("가장 최근에 시작된 수면 기록 조회");
        Optional<Record> lastedSleepRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, sleeping);

        log.info("이미 시작된 수면 기록 확인");
        if (lastedSleepRecord.isEmpty()) {
            throw new RestApiException(CustomRecordErrorCode.SLEEP_NOT_STARTED);
        }

        // 수면 종료 시간 기록
        lastedSleepRecord.get().setEndTime(LocalDateTime.now());
        lastedSleepRecord.get().setIsDone(true);
        log.info("수면 종료 기록 완료. 종료시간 : {}", lastedSleepRecord.get().getEndTime());

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();
        HashMap<String, Object> data = new HashMap<>();
        data.put("bloodSugarLevel", bloodSugarLevel);

        return ResponseDto.builder()
                .message("수면을 종료합니다.")
                .data(bloodSugarLevel)
                .build();


    }

}
