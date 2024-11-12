package com.onetwo.mongddang.domain.game.gameLog.application;

import com.onetwo.mongddang.domain.game.achievement.model.Achievement.AchievementCategory;
import com.onetwo.mongddang.domain.game.gameLog.errors.CustomGameLogErrorCode;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog.GameLogCategory;
import com.onetwo.mongddang.domain.game.gameLog.repository.GameLogRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameLogUtils {


    public final GameLogRepository gameLogRepository;
    public final UserRepository userRepository;


    /**
     * 게임 로그 초기화 - 회원가입 시 호출
     *
     * @param id 사용자 id
     */
    @Transactional
    public void initGameLog(Long id) {
        log.info("initGameLog - userId: {}", id);

        // id 에 해당하는 User 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 사용자에 대한 기존 게임 로그가 존재하는지 확인
        if (gameLogRepository.existsByChild(user)) {
            throw new RestApiException(CustomGameLogErrorCode.GAME_LOG_ALREADY_INITIALIZED);
        }

        // 초기화용 게임 로그 생성
        GameLog gameLog = GameLog.builder()
                .child(user)
                .mealCount(0)
                .exerciseCount(0)
                .sleepingCount(0)
                .medicationCount(0)
                .build();

        gameLogRepository.save(gameLog);
    }


    /**
     * 게임 로그 추가
     *
     * @param child       사용자
     * @param category 게임 로그 카테고리
     * @return 추가된 게임 로그
     */
    @Transactional
    public GameLog addGameLog(User child, GameLogCategory category) {
        log.info("addGameLog - userId: {}, category: {}", child.getId(), category);

        // userId에 해당하는 GameLog 조회
        GameLog foundGameLog = gameLogRepository.findTopByChildOrderByIdDesc(child)
                .orElseThrow(() -> new RestApiException(CustomGameLogErrorCode.GAME_LOG_NOT_FOUND));

        // 게임 로그 생성
        GameLog newGameLog = GameLog.builder()
                .child(child)
                .mealCount(category.equals(GameLogCategory.meal_count) ? foundGameLog.getMealCount() + 1 : foundGameLog.getMealCount())
                .exerciseCount(category.equals(GameLogCategory.exercise_count) ? foundGameLog.getExerciseCount() + 1 : foundGameLog.getExerciseCount())
                .sleepingCount(category.equals(GameLogCategory.sleeping_count) ? foundGameLog.getSleepingCount() + 1 : foundGameLog.getSleepingCount())
                .medicationCount(category.equals(GameLogCategory.medication_count) ? foundGameLog.getMedicationCount() + 1 : foundGameLog.getMedicationCount())
                .build();

        // 게임 로그 저장
        GameLog savedGameLog = gameLogRepository.save(newGameLog);
        log.info("게임 로그 저장 완료 => 카테고리 {} + 1", category);

        return savedGameLog;
    }


    // 게임 로그 횟수 조회
    public int getGameLogCountByCategory(Long userId, AchievementCategory category) {
        log.info("getGameLogCountByCategory - userId: {}, category: {}", userId, category);

        // userId에 해당하는 GameLog 조회
        GameLog gameLog = gameLogRepository.findTopByChildIdOrderByIdDesc(userId)
                .orElseThrow(() -> new RestApiException(CustomGameLogErrorCode.GAME_LOG_NOT_FOUND));

        int count = -1;
        switch (category) {
            case meal:
                count = gameLog.getMealCount() >= count ? gameLog.getMealCount() : -1;
                break;
            case exercise:
                count = gameLog.getExerciseCount() >= count ? gameLog.getExerciseCount() : -1;
                break;
            case sleeping:
                count = gameLog.getSleepingCount() >= count ? gameLog.getSleepingCount() : -1;
                break;
            case medication:
                count = gameLog.getMedicationCount() >= count ? gameLog.getMedicationCount() : -1;
                break;
        }

        return count;
    }

}
