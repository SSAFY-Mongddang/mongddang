package com.onetwo.mongddang.domain.record.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.record.dto.record.ResponseBloodSugarDto;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.medication;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordMedicationService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final MissionLogUtils missionLogUtils;
    private final GameLogUtils gameLogUtils;


    /**
     * 복약 기록하기
     *
     * @param childId 복약 기록을 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto checkMedication(Long childId) {
        log.info("checkMedication childId: {}", childId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        Record newMedicationRecord = Record.builder()
                .child(child)
                .category(medication)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .content(null)
                .imageUrl(null)
                .isDone(true)
                .mealTime(null)
                .build();

        recordRepository.save(newMedicationRecord);
        log.info("복약 확인 기록 완료. : {}", newMedicationRecord.getStartTime());

        // 미션 업데이트
        missionLogUtils.completeMission(child, MissionDto.Mission.first_medication);

        // 게임 로그 업데이트
        gameLogUtils.addGameLog(child, GameLog.GameLogCategory.medication_count);

        ResponseBloodSugarDto bloodSugarLevel = ResponseBloodSugarDto.builder()
                .bloodSugarLevel(100L)
                .build();

        return ResponseDto.builder()
                .message("복약을 확인합니다.")
                .data(bloodSugarLevel)
                .build();
    }
}
