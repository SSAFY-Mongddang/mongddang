package com.onetwo.mongddang.domain.record.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.s3.S3ImageService;
import com.onetwo.mongddang.common.utils.DateTimeUtils;
import com.onetwo.mongddang.common.utils.JsonUtils;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.record.errors.CustomRecordErrorCode;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.application.CtoPUtils;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.sleeping;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSleepingService {


    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final CtoPUtils ctoPUtils;
    private final DateTimeUtils dateTimeUtils;
    private final S3ImageService s3ImageService;
    private final JsonUtils jsonUtils;
    private final MissionLogUtils missionLogUtils;
    private final GameLogUtils gameLogUtils;


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

        log.info("가장 최근에 시작된 수면 기록 조회");
        Optional<Record> lastedSleepRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, sleeping);

        log.info("이미 시작된 수면 기록 확인");
        if (lastedSleepRecord.isPresent()) {
            throw new RestApiException(CustomRecordErrorCode.SLEEP_ALREADY_STARTED);
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

        return ResponseDto.builder()
                .message("수면을 시작합니다.")
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
        Record sleepRecord = lastedSleepRecord.get();
        sleepRecord.setEndTime(LocalDateTime.now());
        sleepRecord.setIsDone(true);

        recordRepository.save(sleepRecord);
        log.info("수면 종료 기록 완료. 종료시간 : {}", sleepRecord.getEndTime());

        return ResponseDto.builder()
                .message("수면을 종료합니다.")
                .build();
    }

}
