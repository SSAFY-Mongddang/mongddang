package com.onetwo.mongddang.domain.user.jwt;

import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// jwt에서 userid 추출하는 서비스입니다.
@Service
@RequiredArgsConstructor
public class JwtExtratService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public Long jwtFindId(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분
            try {
                // 2. JWT 토큰에서 이메일 추출
                email = jwtTokenProvider.getEmailFromToken(token);
                User user = userRepository.findByEmail(email).orElseThrow(() -> {
                    throw new RuntimeException(CustomUserErrorCode.USER_NOT_FOUND.getMessage());
                });
                return user.getId();
            } catch (JwtException e) {
                System.out.println("jwtExtratService JWT 토큰이 유효하지 않습니다." + e);
                throw new RuntimeException(CustomUserErrorCode.AUTHENTICATED_FAILED.getMessage());
            }
        } else{
            System.out.println("jwtExtratService JWT 토큰이 유효하지 않습니다.");
            throw new RuntimeException(CustomUserErrorCode.AUTHENTICATED_FAILED.getMessage());
        }
    }
}
