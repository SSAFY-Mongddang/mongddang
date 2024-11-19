package com.onetwo.mongddang.domain.game.achievement.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomAchievementErrorCode implements ErrorCode {
    NOT_FOUND_ACHIEVEMENT_LIST(HttpStatus.BAD_REQUEST, "AC000", "업적 목록 조회에 실패했습니다."),
    INVALID_ACHIEVEMENT_ID(HttpStatus.NOT_FOUND, "AC001", "잘못된 업적 ID 입니다."),
    ACHIEVEMENT_NOT_UNLOCKED(HttpStatus.BAD_REQUEST, "AC002", "조건이 달성되지 않은 업적입니다."),
    ACHIEVEMENT_REWARD_CLAIM(HttpStatus.BAD_REQUEST, "AC003", "업적 보상 수령에 실패했습니다."),
    ACHIEVEMENT_ALREADY_REWARDED(HttpStatus.BAD_REQUEST, "AC004", "이미 수령한 업적입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
