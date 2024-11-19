package com.onetwo.mongddang.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FcmConfig {

    @Value("${firebase.credentials.path}")
    private String firebaseCredentialsPath;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        // 이미 초기화된 FirebaseApp이 있는지 확인
        if (FirebaseApp.getApps().isEmpty()) {
            // ClassPathResource를 사용해 InputStream을 얻습니다.
            try (InputStream serviceAccount = new ClassPathResource(firebaseCredentialsPath).getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                return FirebaseApp.initializeApp(options); // 새로운 인스턴스 생성
            }
        } else {
            return FirebaseApp.getInstance(); // 이미 존재하는 인스턴스 반환
        }
    }
}

