package com.onetwo.mongddang.domain.user.error;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomCtoPErrorCode implements ErrorCode {
    INVALID_INVITATION_CODE(HttpStatus.NOT_FOUND, "CP000", "해당 초대코드를 가진 어린이가 없습니다."),
    ALREADY_LINKED(HttpStatus.BAD_REQUEST, "CP001", "이미 연결된 어린이-보호자 관계입니다."),
    CHILD_NOT_LINKED(HttpStatus.BAD_REQUEST, "CP002", "연결되지 않은 어린이입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}