package com.onetwo.mongddang.domain.fcm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.mongddang.domain.fcm.dto.Notification;
import com.onetwo.mongddang.domain.fcm.dto.FcmMessage;
import com.onetwo.mongddang.domain.fcm.errors.CustomFcmErrorCode;
import com.onetwo.mongddang.domain.fcm.model.FcmToken;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.repository.FcmTokenRepository;
import com.onetwo.mongddang.domain.fcm.repository.PushLogRepository;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PushNotificationService {

    private final PushLogRepository pushLogRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final GetAccessToken getAccessToken;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @Value("${fcm.url.postfix}")
    private String urlPostfix;

    @Value("${fcm.url.prefix}")
    private String urlPrefix;

    @Value("${fcm.project.id}")
    private String projectId;

    // 푸시 알림을 보내는 서비스
    public void sendPushNotification(User user, Notification notification, PushLog.Category category){
        // fcm토큰 찾기
        Optional<FcmToken> token = fcmTokenRepository.findByUser(user);

        // FCM 토큰이 없는 경우 메서드를 종료하여 알림을 건너뜀
        if (token.isEmpty()) {
            log.warn("User {}에 대한 FCM 토큰이 존재하지 않아 알림을 건너뜁니다.", user.getId());
            return;
        }

        String fcmToken = token.get().getToken();

        // 메시지 만들기
        String message = makeMessage(fcmToken,notification,category);

        // HTTP 요청 엔티티 생성 : 본문과 헤더 함께 담아 요청에 사용
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // 컨텐츠 타입
        //OAuth 2.0 사용
        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer " + getAccessToken.getAccessToken()); // 토큰

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        String fcmRequestUrl = urlPrefix + projectId + urlPostfix; // 요청 보낼 url

        // WebClient를 통한 동기 HTTP 요청
        String response = webClient.post()
                .uri(fcmRequestUrl) // 요청 URL 설정
                .headers(headers -> headers.addAll(httpHeaders)) // 헤더 추가
                .bodyValue(message) // 요청 본문 추가
                .retrieve() // 응답 받기
                .bodyToMono(String.class) // 응답을 Mono<String>으로 변환
                .block(); // 동기 방식으로 응답 대기

        // 응답 처리
        if (response == null || response.isEmpty()) {
            log.error("FCM 서버에서 빈 응답이 돌아왔습니다.");
        } else {
            // 성공 시 저장
            log.info("FCM 서버 응답: {}", response);
//            savePushNotification(user, notification.getMessage(), category);
        }
    }

    // 보낸 푸시 알림을 알림 로그에 저장하는 서비스
    public void savePushNotification(User user, String content, PushLog.Category category){
        PushLog pushLog = PushLog.builder()
                .user(user)
                .isNew(true)
                .content(content)
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();
        // PushLogRepository를 통해 저장
        pushLogRepository.save(pushLog);
    }

    public String makeMessage(String targetToken, Notification notification, PushLog.Category category) {
        // data 설정
        FcmMessage.Data messageData = FcmMessage.Data.builder()
                .receiverNickname(notification.getReceiver().getNickname())
                .childNickname(notification.getChild().getNickname())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .category(category)
                .medicationId(notification.getMedicationId())
                .build();
        log.info("data 설정 : " + notification.getMedicationId().toString());
        // notification 설정
        FcmMessage.Notification messageNotification = FcmMessage.Notification.builder()
                .title(notification.getTitle()) // 알림 제목 설정
                .body(notification.getMessage()) // 알림 본문 설정
                .build();

        // message 설정 - data와 notification을 함께 포함
        FcmMessage.Message message = FcmMessage.Message.builder()
                .token(targetToken)
                .data(messageData)               // 데이터 메시지
                .notification(messageNotification) // 알림 메시지
                .build();

        // 최종 fcm message 설정
        FcmMessage fcmMessage = FcmMessage.builder()
                .validateOnly(false)
                .message(message)
                .build();

        try {
            return objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            log.error("메세지 보낼 때 JSON 변환 에러", e);
            throw new RestApiException(CustomFcmErrorCode.CONVERTING_JSON_ERROR);
        }
    }

}
