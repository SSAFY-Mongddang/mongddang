package com.onetwo.mongddang.domain.missionlog.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.coinLog.application.CoinLogUtils;
import com.onetwo.mongddang.domain.game.coinLog.model.CoinLog;
import com.onetwo.mongddang.domain.game.coinLog.repository.CoinLogRepository;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.missionlog.dto.ResponseMissionListDto;
import com.onetwo.mongddang.domain.missionlog.dto.ResponseMissionRewardDto;
import com.onetwo.mongddang.domain.missionlog.errors.CustomMissionLogErrors;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionLogService {


    private final MissionLogRepository missionLogRepository;
    private final UserRepository userRepository;
    private final MissionLogUtils missionLogUtils;
    private final CoinLogRepository coinLogRepository;
    private final CoinLogUtils coinLogUtils;


    /**
     * 오늘의 미션 목록 조회
     *
     * @param childId 유저 ID
     * @return ResponseDto
     */
    public ResponseDto getTodayMissionLogList(Long childId) {
        log.info("오늘의 미션 목록 조회 시도 (In English : Attempt to view today's mission list.)");

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        List<MissionLog> todayMissionList = missionLogRepository.findByChildAndCreatedAtBetween(child, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0), LocalDateTime.now().withHour(23).withMinute(59).withSecond(59));

        List<ResponseMissionListDto> missionListDto = todayMissionList.stream()
                .map(missionLog -> ResponseMissionListDto.builder()
                        .name(missionLog.getCategory())
                        .reward(missionLog.getReward())
                        .status(missionLog.getStatus())
                        .build())
                .toList();

        return ResponseDto.builder()
                .message("미션 목록 조회에 성공했습니다.")
                .data(missionListDto)
                .build();
    }

    /**
     * 미션 보상 수령
     *
     * @param missionId 미션 ID
     * @param childId   유저 ID
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto rewardMission(Long missionId, Long childId) {
        log.info("미션 보상 수령 시도 (In English : Attempt to receive mission rewards.)");

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        log.info("오늘의 미션 조회 (In english: Find today's mission)");
        MissionLog todayMissionLog = missionLogRepository.findByIdAndCreatedAtBetween(missionId, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59)).orElse(null);

        // 보상 수령 시점에 날짜가 바뀌어 미션을 찾지 못한 경우
        if (todayMissionLog == null) {
            log.info("미션을 찾지 못한 경우 미션 생성 (In english: Create mission if mission not found)");
            missionLogUtils.createMission(child.getId());
            log.info("미션 생성 완료 (In english: Mission creation completed)");

            throw new RestApiException(CustomMissionLogErrors.TODAY_MISSION_CREATED);
        }

        // 보상 수령 시점에 미션 상태가 not_rewardable 인 경우
        if (todayMissionLog.getStatus().equals(MissionDto.Status.not_rewardable)) {
            throw new RestApiException(CustomMissionLogErrors.CONDITION_NOT_MET);
        }

        // 보상 수령 시점에 미션 상태가 already_rewarded 인 경우
        if (todayMissionLog.getStatus().equals(MissionDto.Status.already_rewarded)) {
            throw new RestApiException(CustomMissionLogErrors.MISSION_ALREADY_COMPLETED);
        }

        // 보상 수령 시점에 미션 상태가 rewardable 인 경우 보상 지급
        todayMissionLog.setStatus(MissionDto.Status.already_rewarded);

        // 코인 지급
        CoinLog newCoinLog = coinLogUtils.rewardCoin(childId, CoinLog.CoinCategory.mission, todayMissionLog.getReward().intValue());

        // 새로운 코인 보유량 반환
        ResponseMissionRewardDto missionRewardDto = ResponseMissionRewardDto.builder()
                .rewards(newCoinLog.getCoin())
                .build();

        return ResponseDto.builder()
                .message("미션 보상 수령에 성공했습니다.")
                .data(missionRewardDto)
                .build();

    }


}
