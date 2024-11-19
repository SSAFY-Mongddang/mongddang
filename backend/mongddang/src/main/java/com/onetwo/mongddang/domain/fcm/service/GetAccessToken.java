package com.onetwo.mongddang.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.onetwo.mongddang.domain.fcm.errors.CustomFcmErrorCode;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAccessToken{

    @Value("${firebase.credentials.path}")
    private String firebaseKeyPath;

    public String getAccessToken() {
        try {
            //서비스 계정 키 파일 불러오기
            final GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseKeyPath).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform")); //cloud-platform 범위 : FCM을 포함한 여러 Google API에 접근할 수 있는 권한을 요청

            googleCredentials.refreshIfExpired(); // 토큰 만료시 자동으로 새 토큰 요청

            return googleCredentials.getAccessToken().getTokenValue(); // 토큰 문자열로 반환
        }
        catch (IOException e) {
            log.error("구글 토큰 요청 에러", e);
            throw new RestApiException(CustomFcmErrorCode.NO_ACCESS_TOKEN);
        }
    }




}
