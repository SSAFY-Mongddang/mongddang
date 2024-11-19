package com.onetwo.mongddang.domain.game.gameLog.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomGameLogErrorCode implements ErrorCode {
    GAME_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "CL000", "해당 유저의 게임 로그를 찾을 수 없습니다."),
    GAME_LOG_ALREADY_INITIALIZED(HttpStatus.BAD_REQUEST, "GL001", "해당 유저에 대한 게임 로그가 이미 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
