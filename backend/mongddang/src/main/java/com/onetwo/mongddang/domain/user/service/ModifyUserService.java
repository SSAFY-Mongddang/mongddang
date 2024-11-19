package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.dto.CheckNicknameRequestDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModifyUserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto modifyUser(CheckNicknameRequestDto checkNicknameRequestDto,Long userID) {

        Optional<User> user = userRepository.findById(userID);
        String newNickname = checkNicknameRequestDto.getNickname();


        // 사용자 정보 있는지 확인
        if (user == null) {
            throw new RestApiException(CustomUserErrorCode.USER_NOT_FOUND);
        }

        // 이전과 동일한 닉네임인지 확인
        if (newNickname.equals(user.get().getNickname())) {
            throw new RestApiException(CustomUserErrorCode.UNCHANGED_NICKNAME);
        }

        // 업데이트
        User newUser = user.get().toBuilder()
                .nickname(newNickname)
                .build();
        userRepository.save(newUser);

        //response
        ResponseDto response = ResponseDto.builder()
                .message("닉네임 수정에 성공했습니다.")
                .build();

        return response;
    }
}
