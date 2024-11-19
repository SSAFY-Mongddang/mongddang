package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {// 사용자 정보를 DB에서 조회
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            // 사용자가 있는 경우: 정상적으로 사용자 정보를 반환
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    "",  // 비밀번호는 비워둠 (OAuth로 인증)
                    true,  // 계정 활성화
                    true,  // 계정 만료 여부
                    true,  // 자격 증명 만료 여부
                    true,  // 계정 잠금 여부
                    List.of(() -> user.getRole().toString())  // 권한 반환
            );
        } else {
            // 사용자가 없는 경우: 기본 정보를 사용하여 인증 통과
            return new org.springframework.security.core.userdetails.User(
                    email,
                    "",  // 비밀번호는 비워둠
                    true,  // 계정 활성화
                    true,  // 계정 만료 여부
                    true,  // 자격 증명 만료 여부
                    true,  // 계정 잠금 여부
                    List.of(() -> "newUser")  // 기본 권한 설정
            );
        }
    }
}
