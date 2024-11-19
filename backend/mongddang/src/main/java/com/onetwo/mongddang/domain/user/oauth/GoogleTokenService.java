package com.onetwo.mongddang.domain.user.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.errors.exception.RestApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenService {

    @Value("${google.client-id}")
    private String CLIENT_ID;  // 구글 클라이언트 ID

    public GoogleIdToken.Payload getPayloadFromIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))  // 클라이언트 ID 검증
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);  // IdToken 검증
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            return payload;
        } else {
            throw new RestApiException(CustomUserErrorCode.INVALID_ID_TOKEN);
        }
    }

    public String getEmailFromIdToken(String idTokenString) {
        GoogleIdToken.Payload payload = null;
        try {
            payload = getPayloadFromIdToken(idTokenString);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RestApiException(CustomUserErrorCode.INVALID_ID_TOKEN);
        }

        return (String) payload.get("email");
    }

}
