package com.onetwo.mongddang.domain.vital.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomVitalErrorCode implements ErrorCode {

    VITAL_LOG_NOT_FOUNT(HttpStatus.NOT_FOUND, "VT000", "바이탈 로그를 찾을 수 없습니다."),
    VITAL_GENERATE_GPT_FAILED(HttpStatus.BAD_REQUEST, "VT001", "GPT 요약 생성 요청에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

