package com.onetwo.mongddang.domain.mealplan.error;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomMealplanErrorCode implements ErrorCode {
    SCHOOL_INFO_RETR_FAIL(HttpStatus.BAD_GATEWAY, "MP000", "학교 정보 조회에 실패했습니다.\n" +
            "(학교명이 올바른지 확인해주세요.)"),
    MEAL_INFO_RETR_FAIL(HttpStatus.BAD_GATEWAY, "MP001", "급식 정보 조회에 실패했습니다."),
    MEAL_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "MP002", "급식 정보를 찾을 수 없습니다."),
    MEAL_TIME_NOT_IN_ENUM(HttpStatus.BAD_REQUEST, "MP003", "mealTime은 breakfast, lunch, dinner 중 하나입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}