package com.onetwo.mongddang.domain.game.achievement.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.achievement.dto.ResponseAchievementListDto;
import com.onetwo.mongddang.domain.game.achievement.errors.CustomAchievementErrorCode;
import com.onetwo.mongddang.domain.game.achievement.model.Achievement;
import com.onetwo.mongddang.domain.game.achievement.repository.AchievementRepository;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.title.errors.CustomTitleErrorCode;
import com.onetwo.mongddang.domain.game.title.model.MyTitle;
import com.onetwo.mongddang.domain.game.title.model.Title;
import com.onetwo.mongddang.domain.game.title.repository.MyTitleRepository;
import com.onetwo.mongddang.domain.game.title.repository.TitleRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final TitleRepository titleRepository;
    private final MyTitleRepository myTitleRepository;
    private final UserRepository userRepository;
    private final GameLogUtils gameLogUtils;

    // 업적 목록 조회
    @Transactional
    public ResponseDto getAchievementList(Long childId) {
        log.info("getAchievementList childId: {}", childId);

        List<Achievement> achievementList = achievementRepository.findAll();
        log.info("achievementList: {}", achievementList);
        List<ResponseAchievementListDto> achievementListDto = achievementList.stream()
                .map(achievement -> {
                    // 업적에 해당하는 칭호 조회
                    Title title = titleRepository.findById(achievement.getId())
                            .orElseThrow(() -> new RestApiException(CustomTitleErrorCode.INVALID_TITLE_ID));
                    log.info("title: {}", title.getName());

                    // 번호에 해당하는 칭호 조회
                    Optional<MyTitle> myTitle = myTitleRepository.findByTitleIdAndChildId(title.getId(), childId);

                    // 업적 달성에 요구되는 횟수
                    int executionCount = gameLogUtils.getGameLogCountByCategory(childId, achievement.getCategory());
                    log.info("executionCount: {}", executionCount);

                    // 업적 달성 여부 -1: 달성하지 않음
                    boolean isAchieved = executionCount != -1;

                    log.info("isAchieved: {}", isAchieved);
                    return ResponseAchievementListDto.builder()
                            .titleId(title.getId())
                            .titleName(title.getName())
                            .description(achievement.getDescription())
                            .executionCount(executionCount)
                            .count(achievement.getCount())
                            .category(achievement.getCategory())
                            .isOwned(myTitle.isPresent() && isAchieved)
                            .isNew((isAchieved && myTitle.isPresent()) ? myTitle.get().getIsNew() : false)
                            .isMain((isAchieved && myTitle.isPresent()) ? myTitle.get().getIsMain() : false)
                            .build();
                })
                .toList();

        ResponseDto responseDto = ResponseDto.builder()
                .message("업적 목록 조회에 성공했습니다.")
                .data(achievementListDto)
                .build();

        return responseDto;
    }

    // 업적 보상 수령
    @Transactional
    public ResponseDto claimAchievementReward(Long childId, Long achievementId) {
        log.info("claimAchievementReward childId: {}, achievementId: {}", childId, achievementId);

        User user = userRepository.findById(childId).orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("user: {}", user.getEmail());

        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new RestApiException(CustomAchievementErrorCode.INVALID_ACHIEVEMENT_ID));
        log.info("achievement: {}", achievement.getDescription());

        Title title = titleRepository.findByAchievementId(achievement.getId())
                .orElseThrow(() -> new RestApiException(CustomAchievementErrorCode.INVALID_ACHIEVEMENT_ID));
        log.info("title: {}", title.getName());

        MyTitle myTitleOptional = myTitleRepository.findByTitleIdAndChildId(title.getId(), childId)
                .orElse(null);
        log.info("myTitleOptional");

        // 이미 보상을 수령한 경우
        if (myTitleOptional != null) {
            throw new RestApiException(CustomAchievementErrorCode.ACHIEVEMENT_ALREADY_REWARDED);
        }

        // 업적 달성 횟수
        int executionCount = gameLogUtils.getGameLogCountByCategory(childId, achievement.getCategory());

        // 업적 달성 횟수가 부족한 경우
        if (executionCount < achievement.getCount()) {
            throw new RestApiException(CustomAchievementErrorCode.ACHIEVEMENT_NOT_UNLOCKED);
        }

        MyTitle newMyTitle = MyTitle.builder()
                .child(user)
                .title(title)
                .isNew(true)
                .isMain(false)
                .createdAt(LocalDateTime.now())
                .build();

        // 칭호 소유 처리
        myTitleRepository.save(newMyTitle);

        ResponseDto responseDto = ResponseDto.builder()
                .message("업적 보상 수령에 성공했습니다.")
                .build();

        return responseDto;
    }

    // 메인 업적 설정
}
