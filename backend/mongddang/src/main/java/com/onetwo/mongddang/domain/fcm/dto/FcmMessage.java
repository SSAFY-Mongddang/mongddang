package com.onetwo.mongddang.domain.fcm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class FcmMessage {

    @JsonProperty("validate_only")
    private final boolean validateOnly; // 테스트 모드 설정
    private final Message message; // 메시지 본문 객체

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Message {

        private final Data data; // 수신자에게 전달될 내용
        private final String token; // 수신자 식별 코드. db에 저장되어있음
    }

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Data {

        private final String receiverNickname; // 알림 수신인의 닉네임
        private final String childNickname; // 알림 대상자의 닉네임(이상혈당 지속시)
        private final String title; // 제목 ("혈당 이상")
        private final String message; // 내용 ("고혈당입니다!")
    }
}
