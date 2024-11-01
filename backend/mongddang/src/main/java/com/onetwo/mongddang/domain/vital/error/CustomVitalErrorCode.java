package com.onetwo.mongddang.domain.vital.error;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomVitalErrorCode implements ErrorCode {
   THIS_USER_VITAL_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "VT000","해당 유저의 바이탈 정보를 찾을 수 없습니다."),
   THIS_DATE_IS_NOT_VALID(HttpStatus.NOT_FOUND, "VT003","미래날짜의 바이탈 정보는 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
