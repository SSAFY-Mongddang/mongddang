package com.onetwo.mongddang.domain.game.coinLog.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomCoinLogErrorCode implements ErrorCode {
    NOT_FOUND_COIN_LOG(HttpStatus.NOT_FOUND, "CN000", "보유 코인 조회에 실패했습니다."),
    INSUFFICIENT_COIN(HttpStatus.BAD_REQUEST, "CN001", "코인이 부족합니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
