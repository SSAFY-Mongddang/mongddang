package com.onetwo.mongddang.domain.user.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.dto.LoginRequestDto;
import com.onetwo.mongddang.domain.user.dto.UserInfoDto;
import com.onetwo.mongddang.domain.user.jwt.JwtTokenProvider;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.user.service.CustomUserDetailsService;
import com.onetwo.mongddang.domain.user.service.GetUserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final GoogleTokenService googleTokenService;
    private final GetUserInfoService getUserInfoService;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // yyyy-MM-dd 형식으로 설정

    public LoginFilter(String defaultFilterProcessesUrl,
                       AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       CustomUserDetailsService userDetailsService,
                       UserRepository userRepository,
                       GoogleTokenService googleTokenService,
                       GetUserInfoService getUserInfoService) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.googleTokenService = googleTokenService;
        this.getUserInfoService = getUserInfoService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        try {
            // 요청 -> LoginRequest 객체
            LoginRequestDto loginRequest = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<LoginRequestDto>(){});

            // IdToken이 없으면 오류 발생
            if (loginRequest.getIdToken() == null || loginRequest.getIdToken().isEmpty()) {
                throw new AuthenticationServiceException("ID token is null or empty.");
            }

            // IdToken에서 이메일 추출
            String email;
            try {
                email = googleTokenService.getEmailFromIdToken(loginRequest.getIdToken());
            } catch (Exception e) {
                throw new AuthenticationServiceException("error occurred while validating the ID token");
            }

            // ID 토큰을 CustomOAuthToken으로 래핑 -> 인증 매니저에 전달
            return getAuthenticationManager().authenticate(
                    new CustomOAuthToken(loginRequest.getIdToken(),email)
            );
        } catch (IOException e) {
            // JSON 파싱 오류 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request body");
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String email = (String) authResult.getPrincipal();

        // DB에서 사용자 정보 확인
        Optional<User> userOptional = userRepository.findByEmail(email);
        boolean is_registered = userOptional.isPresent();

        //토큰 생성
        String jwtToken;
        if (is_registered) {
            User user = userOptional.get();
            jwtToken = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().toString(), user.getId());
        } else {
            // 사용자 없으면 null값 반환
            jwtToken = null;
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("accessToken", jwtToken);
        dataMap.put("isRegistered", is_registered);

        // 기존 회원인 경우 사용자 정보 전달
        if (is_registered) {
            User user = userOptional.get();
            UserInfoDto userInfoDto = getUserInfoService.getUserInfoDto(user,user.getId());
            dataMap.put("userInfo", userInfoDto);
        }

        ResponseDto responseDto = ResponseDto.builder()
                .message("로그인 성공")
                .data(dataMap)
                .build();

        // ObjectMapper로 JSON 변환
        String jsonResponse = objectMapper.writeValueAsString(responseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + failed.getMessage());
    }
}
