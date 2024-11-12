package com.onetwo.mongddang.domain.fcm.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomFcmErrorCode implements ErrorCode {

    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "N000", "fcm 토큰을 찾을 수 없습니다."),
    SEND_AND_SAVE_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "N001", "알림 전송 및 저장에 실패했습니다."),
    CONVERTING_JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"N002", "(fcm) json 변환 과정에서 에러가 발생했습니다."),
    NO_ACCESS_TOKEN(HttpStatus.SERVICE_UNAVAILABLE,"N003","(fcm) 구글 access 토큰 요청 에러가 발생했습니다."),
    ALREADY_READ(HttpStatus.BAD_REQUEST,"N004","이미 읽은 알림입니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND,"N005","알림을 찾을 수 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
