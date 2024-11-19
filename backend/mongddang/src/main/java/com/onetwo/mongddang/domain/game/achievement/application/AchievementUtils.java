package com.onetwo.mongddang.domain.game.achievement.application;

import com.onetwo.mongddang.domain.game.achievement.model.Achievement;
import com.onetwo.mongddang.domain.game.achievement.repository.AchievementRepository;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.title.errors.CustomTitleErrorCode;
import com.onetwo.mongddang.domain.game.title.model.MyTitle;
import com.onetwo.mongddang.domain.game.title.model.Title;
import com.onetwo.mongddang.domain.game.title.repository.MyTitleRepository;
import com.onetwo.mongddang.domain.game.title.repository.TitleRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AchievementUtils {

    private final AchievementRepository achievementRepository;
    private final TitleRepository titleRepository;
    private final MyTitleRepository myTitleRepository;
    private final UserRepository userRepository;
    private final GameLogUtils gameLogUtils;


    // 획득 가능한 칭호 조회
    public boolean getIsExistRewardableTitle(Long childId) {
        log.info("getIsExistRewardableTitle childId: {}", childId);

        List<Achievement> achievementList = achievementRepository.findAll();

        for (Achievement achievement : achievementList) {
            // 업적에 해당하는 칭호 조회
            Title title = titleRepository.findById(achievement.getId())
                    .orElseThrow(() -> new RestApiException(CustomTitleErrorCode.INVALID_TITLE_ID));
            log.info("title: {}", title.getId());

            // 번호에 해당하는 칭호 조회
            Optional<MyTitle> myTitle = myTitleRepository.findByTitleIdAndChildId(title.getId(), childId);
            log.info("myTitle: {}", myTitle.isEmpty());

            // 업적 달성에 요구되는 횟수
            int executionCount = gameLogUtils.getGameLogCountByCategory(childId, achievement.getCategory());
            log.info("executionCount: {}", executionCount);

            if (executionCount >= achievement.getCount() && myTitle.isEmpty()) {
                return true;
            }
        }

        return false;
    }


}
