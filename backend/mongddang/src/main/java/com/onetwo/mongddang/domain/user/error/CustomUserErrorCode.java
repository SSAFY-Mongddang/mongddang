package com.onetwo.mongddang.domain.user.error;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomUserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U000", "사용자 정보가 없습니다."),
    NICKNAME_IS_EXISTS(HttpStatus.CONFLICT, "U001", "이미 존재하는 nickname입니다."),
    NOT_CHILD(HttpStatus.FORBIDDEN, "U002", "어린이가 아닙니다."),
    NOT_PROTECTOR(HttpStatus.FORBIDDEN, "U003", "보호자가 아닙니다."),
    UNCHANGED_NICKNAME(HttpStatus.BAD_REQUEST, "U004", "이전과 동일한 nickname입니다."),
    INVALID_ID_TOKEN(HttpStatus.BAD_REQUEST, "U005", "잘못된 id토큰입니다."),
    AUTHENTICATED_FAILED(HttpStatus.UNAUTHORIZED, "U006", "jwt 토큰이 유효하지 않습니다."),
    USER_IS_EXISTS(HttpStatus.CONFLICT, "U007", "이미 존재하는 사용자입니다."),
    THIS_NICKNAME_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U008", "존재하지 않는 nickname입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}