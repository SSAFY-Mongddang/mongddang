package com.onetwo.mongddang.domain.user.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomOAuthToken extends AbstractAuthenticationToken {

    private final String idToken;
    private final String email;

    // 인증 전: 권한이 없는 상태로 초기화하는 생성자
    public CustomOAuthToken(String idToken, String email) {
        super(null);  // 권한이 없는 상태로 초기화
        this.idToken = idToken;
        this.email = email;
        setAuthenticated(false);  // 처음에는 인증되지 않은 상태로 설정
    }

    // 인증 후: 권한 정보를 포함하여 인증된 상태로 설정하는 생성자
    public CustomOAuthToken(String idToken, String email, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);  // 권한 정보를 포함
        this.idToken = idToken;
        this.email = email;
        setAuthenticated(true);  // 인증된 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return idToken;
    }

    @Override
    public Object getPrincipal() {
        return email;  // 이메일을 principal로 반환
    }
}

