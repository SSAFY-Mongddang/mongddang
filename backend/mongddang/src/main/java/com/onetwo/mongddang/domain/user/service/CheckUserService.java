package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckUserService {
    private final UserRepository userRepository;

    /**
     * 해당 유저의 역할이 찾는 역할인지 확인하고 해당 user를 반환합니다.
     *
     * @param userId 역할을 조회할 유저의 id
     * @return user
     */
    @Transactional
    public User isEqualRole(Long userId, String roleToCheck) {
        log.info("isChildCheck userId: {}, roleToCheck: {}", userId, roleToCheck);

        return userRepository.findById(userId)
                .map(user -> {
                    switch (roleToCheck) {
                        case "child":
                            if (!user.getRole().equals(roleToCheck)) {
                                throw new RuntimeException(CustomUserErrorCode.NOT_CHILD.getMessage());
                            }
                            break;

                        case "protector":
                            if (!user.getRole().equals(roleToCheck)) {
                                throw new RuntimeException(CustomUserErrorCode.NOT_PROTECTOR.getMessage());
                            }
                            break;

                        default:
                            throw new RuntimeException(CustomUserErrorCode.NOT_CORRECT_ROLE.getMessage());
                    }
                    return user;
                })
                .orElseThrow(() -> new RuntimeException(CustomUserErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
