package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.dto.CheckNicknameRequestDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckNicknameService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto checkNickname(CheckNicknameRequestDto checkNicknameRequestDto) {
        String nickname = checkNicknameRequestDto.getNickname();

        if (userRepository.existsByNickname(nickname)) {
            throw new RestApiException(CustomUserErrorCode.NICKNAME_IS_EXISTS);
        }

        ResponseDto response = ResponseDto.builder()
                .message("사용 가능한 닉네임입니다.")
                .build();

        return response;
    }
}
