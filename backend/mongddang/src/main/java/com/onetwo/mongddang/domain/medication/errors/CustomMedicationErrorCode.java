package com.onetwo.mongddang.domain.medication.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomMedicationErrorCode implements ErrorCode {
    MEDICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "M000", "복약 기록을 찾을 수 없습니다."),
    MEDICATION_NOT_MATCH(HttpStatus.BAD_REQUEST, "M001", "복약 기록 아이디와 소유자가 일치하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
