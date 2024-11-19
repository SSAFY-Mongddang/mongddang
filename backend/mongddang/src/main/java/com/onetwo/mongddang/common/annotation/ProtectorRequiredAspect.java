package com.onetwo.mongddang.common.annotation;

import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ProtectorRequiredAspect {

    private final JwtExtratService jwtExtratService;
    private final UserRepository userRepository;

    @Autowired
    private HttpServletRequest request; // HttpServletRequest 주입

    @Before("@annotation(com.onetwo.mongddang.common.annotation.ProtectorRequired)")
    public void checkChildRequired() {
        // JWT에서 유저 정보를 추출
        Long id = jwtExtratService.jwtFindId(request); // JWT에서 유저 ID 추출
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다. id: " + id));

        // 유저의 롤 체크
        if (user.getRole() != User.Role.protector) {
            throw new RuntimeException("유저 role 은 protector 여야합니다.");
        }
    }
}
