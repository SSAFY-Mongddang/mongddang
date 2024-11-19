package com.onetwo.mongddang.domain.user.oauth;

import com.onetwo.mongddang.domain.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuthAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // CustomOAuthToken에서 token과 email 정보를 가져옴
        String email = (String) authentication.getPrincipal();
        String idToken = (String) authentication.getCredentials();

        // 이메일을 통해 사용자를 불러옴
        log.info("인증 시도 - 이메일: {}", email);  // 이메일 로그
        var userDetails = customUserDetailsService.loadUserByUsername(email);

        // id 토큰 유효성 검사 필요?
        // 인증된 토큰을 반환
        log.info("인증 성공 - 이메일: {}", userDetails.getUsername());  // 사용자 정보 로그

        return new CustomOAuthToken(idToken, email, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // CustomOAuthToken 타입의 인증 객체만 지원
        return CustomOAuthToken.class.isAssignableFrom(authentication);
    }
}
