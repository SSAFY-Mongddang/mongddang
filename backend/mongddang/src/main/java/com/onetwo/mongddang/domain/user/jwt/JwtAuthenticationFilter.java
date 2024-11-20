package com.onetwo.mongddang.domain.user.jwt;

import com.onetwo.mongddang.domain.user.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 필터를 적용하지 않을 경로
        return path.equals("/api/auth/signup") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/api-docs") ||
                path.startsWith("/api/auth") ||
                path.startsWith("/api/push/bp");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Authorization 헤더에서 JWT 토큰을 가져옴
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 헤더가 없는 경우 401 에러를 반환
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("JWT 토큰이 존재하지 않음 또는 올바르지 않음.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 존재하지 않습니다.");
            return;
        }

        // "Bearer " 이후의 토큰 부분을 가져옴
        token = authorizationHeader.substring(7);

        try {
            // JWT 토큰에서 이메일 추출
            email = jwtTokenProvider.getEmailFromToken(token);
            log.info("토큰 추출 email : " + email);
        } catch (JwtException e) {
            log.error("JWT 토큰이 유효하지 않습니다.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
            return;
        }

        // 3. 이메일이 존재하고, SecurityContext에 인증 정보가 없는 경우
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // 4. 토큰이 유효한 경우, 사용자 인증 설정
            if (jwtTokenProvider.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 5. SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 6. 다음 필터로 진행
        filterChain.doFilter(request, response);

    }
}
