package com.onetwo.mongddang.domain.fcm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FcmMessage {

    @JsonProperty("validate_only")
    private final boolean validateOnly; // 테스트 모드 설정
    private final Message message; // 메시지 본문 객체

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Message {

        private final Data data; // 수신자에게 전달될 내용
        private final Notification notification; // 알림 메시지 추가
        private final String token; // 수신자 식별 코드. db에 저장되어있음
    }

    @RequiredArgsConstructor
    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Data {

        private final String receiverNickname; // 알림 수신인의 닉네임
        private final String childNickname; // 알림 대상자의 닉네임(이상혈당 지속시)
        private final String title; // 제목 ("혈당 이상")
        private final String message; // 내용 ("고혈당입니다!")
        private final PushLog.Category category; // 알림 종류
        private final String medicationId;
    }

    // 알림 메시지 설정을 위한 Notification 클래스
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Notification {
        private final String title; // 알림 제목
        private final String body;  // 알림 내용
    }
}
