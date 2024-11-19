package com.onetwo.mongddang.domain.missionlog.application;


import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.missionlog.model.MissionLog;
import com.onetwo.mongddang.domain.missionlog.repository.MissionLogRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionLogUtils {

    private final MissionLogRepository missionLogRepository;
    private final UserRepository userRepository;

    /**
     * 오늘의 미션을 생성합니다.
     *
     * @param childId 미션을 생성할 유저의 id
     */
    @ChildRequired
    @Transactional
    public void createMission(Long childId) {
        log.info("오늘의 미션 생성 시도");


        User child = userRepository.findById(childId).orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 오늘의 미션이 존재하는지 확인
        if (missionLogRepository.existsByChildAndCreatedAtBetween(child, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59)
        )) {
            log.info("오늘의 미션이 이미 생성되어 있습니다.");
            return;
        }

        // 오늘의 미션 생성
        List<MissionLog> todayMissionList = new ArrayList<>();
        for (MissionDto.Mission mission : MissionDto.Mission.values()) {
            MissionLog todayMission = MissionLog.builder()
                    .child(child)
                    .category(mission)
                    .reward(30L)
                    .status(MissionDto.Status.not_rewardable)
                    .createdAt(LocalDateTime.now())
                    .build();
            todayMissionList.add(todayMission);
        }

        // 오늘의 미션 저장
        missionLogRepository.saveAll(todayMissionList);
        log.info("오늘의 미션을 생성했습니다.");
    }

    /**
     * 미션을 완료합니다.
     *
     * @param child    미션을 완료할 유저
     * @param category 완료할 미션 카테고리
     */
    @Transactional
    public void completeMission(User child, MissionDto.Mission category) {
        log.info("미션 완료 시도 category : {} (In english: Attempt to complete mission)", category);

        log.info("오늘의 미션 조회 (In english: Find today's mission)");
        MissionLog todayMissionLog = missionLogRepository.findTopByChildAndCreatedAtBetweenAndCategoryIs(child, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), category).orElse(null);

        if (todayMissionLog == null) {
            log.info("미션을 찾지 못한 경우 미션 생성 (In english: Create mission if mission not found)");
            this.createMission(child.getId());
            log.info("미션 생성 완료 (In english: Mission creation completed)");

            // 미션 다시 조회
            todayMissionLog = missionLogRepository.findTopByChildAndCreatedAtBetweenAndCategoryIs(child, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), category).orElse(null);

        }

        log.info("미션 업데이트 시도 (In english: Attempt to update mission)");
        if (todayMissionLog.getStatus().equals(MissionDto.Status.not_rewardable)) {
            todayMissionLog.setStatus(MissionDto.Status.rewardable);
            log.info("{} 미션 업데이트 완료 (in english : mission update completed)", category);
        }
    }


}
