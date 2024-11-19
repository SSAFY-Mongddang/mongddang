package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.repository.PushLogRepository;
import com.onetwo.mongddang.domain.game.achievement.application.AchievementUtils;
import com.onetwo.mongddang.domain.game.coinLog.errors.CustomCoinLogErrorCode;
import com.onetwo.mongddang.domain.game.coinLog.model.CoinLog;
import com.onetwo.mongddang.domain.game.coinLog.repository.CoinLogRepository;
import com.onetwo.mongddang.domain.game.mongddang.errors.CustomMongddangErrorCode;
import com.onetwo.mongddang.domain.game.mongddang.model.Mongddang;
import com.onetwo.mongddang.domain.game.mongddang.model.MyMongddang;
import com.onetwo.mongddang.domain.game.mongddang.repository.MongddangRepository;
import com.onetwo.mongddang.domain.game.mongddang.repository.MyMongddangRepository;
import com.onetwo.mongddang.domain.game.title.errors.CustomTitleErrorCode;
import com.onetwo.mongddang.domain.game.title.model.MyTitle;
import com.onetwo.mongddang.domain.game.title.model.Title;
import com.onetwo.mongddang.domain.game.title.repository.MyTitleRepository;
import com.onetwo.mongddang.domain.game.title.repository.TitleRepository;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.missionlog.repository.MissionLogRepository;
import com.onetwo.mongddang.domain.user.dto.MainpageDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final UserRepository userRepository;
    private final MyMongddangRepository myMongddangRepository;
    private final MongddangRepository mongddangRepository;
    private final MyTitleRepository myTitleRepository;
    private final TitleRepository titleRepository;
    private final CoinLogRepository coinLogRepository;
    private final AchievementUtils achievementUtils;
    private final MissionLogRepository missionLogRepository;
    private final PushLogRepository pushLogRepository;

    public ResponseDto getMainInfo(Long userId) {

        // 있는 유저인지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 어린이 맞는지 확인
        if (user.getRole() != User.Role.child) {
            throw new RestApiException(CustomUserErrorCode.NOT_CHILD);
        }
        log.info("real child!");

        // 메인 몽땅
        Optional<MyMongddang> mainMongddangOptional = myMongddangRepository.findByChildIdAndIsMainTrue(userId);

        // 메인 있으면 캐릭터 데이터 뽑기
        if (!mainMongddangOptional.isPresent()) {
            log.info("main mongddang not found");
            throw new RestApiException(CustomMongddangErrorCode.INVALID_COLLECTION_ID);
        }
        log.info("main mongddang is present");
        Optional<Mongddang> mongddang = mongddangRepository.findById(mainMongddangOptional.get().getMongddang().getId());

        // 그 몽땅이 있는 캐릭터라면 정보 뽑아 넣기
        if (!mongddang.isPresent()) {
            log.info("that mongddang not found");
            throw new RestApiException(CustomMongddangErrorCode.INVALID_COLLECTION_ID);
        }

        // 메인 칭호
        Optional<MyTitle> mainTitleOptional = myTitleRepository.findByChildIdAndIsMainTrue(userId);

        // 메인 있으면 칭호 데이터 뽑기
        if (!mainTitleOptional.isPresent()) {
            log.info("main Title not found!");
            throw new RestApiException(CustomTitleErrorCode.INVALID_TITLE_ID);
        }
        log.info("main title is present");
        Optional<Title> title = titleRepository.findById(mainTitleOptional.get().getTitle().getId());

        // 그 칭호가 있는 칭호라면 뽑아넣기
        if (!title.isPresent()) {
            log.info("that title not found!");
            throw new RestApiException(CustomTitleErrorCode.INVALID_TITLE_ID);
        }
        log.info("title is present");

        // coinLog 있는지 확인
        CoinLog coinLog = coinLogRepository.findTopByChildIdOrderByIdDesc(userId)
                .orElseThrow(() -> new RestApiException(CustomCoinLogErrorCode.NOT_FOUND_COIN_LOG));

        // 확인하지 않은 알림 있는지 확인
        Boolean UnreadNotification = pushLogRepository.existsByUserIdAndIsNewTrue(userId);

        // 수령하지 않은 미션 보상 있는지 확인
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1); // 오늘의 끝

        Boolean UnclaimedMissionReward = missionLogRepository.existsByChildAndStatusAndCreatedAtBetween(
                user,
                MissionDto.Status.rewardable,
                startOfDay,
                endOfDay
        );




        // 수령하지 않은 업적 보상 있는지 확인
        Boolean UnclaimedAchivementReward = achievementUtils.getIsExistRewardableTitle(userId);

        // response data
        MainpageDto data = MainpageDto.builder()
                .mainMongddangId(mongddang.get().getId())
                .mainTitleName(title.get().getName())
                .nickname(user.getNickname())
                .coin(coinLog.getCoin())
                .UnreadNotification(UnreadNotification)
                .UnclaimedMissionReward(UnclaimedMissionReward)
                .UnclaimedAchivementReward(UnclaimedAchivementReward)
                .build();

        // response
        ResponseDto response = ResponseDto.builder()
                .message("메인 페이지 정보 조회에 성공했습니다.")
                .data(data)
                .build();

        return response;
    }
}
