package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.dto.ConnectedUserInfoDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyChildrenInfoService {

    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;

    public ResponseDto myChildrenInfo(Long userId) {

        //사용자
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 사용자가 보호자인지 판단
        if (!Objects.equals(user.getRole().toString(), "protector")) {
            throw new RestApiException(CustomUserErrorCode.NOT_PROTECTOR);
        }
        log.info("This User is protector");

        // 연결된 어린이 리스트
        List<User> childrenList = ctoPRepository.findChildByProtectorId(userId);

        // Object에 들어갈 어린이 리스트 초기화
        List<ConnectedUserInfoDto> responseChildrenList = null;
        log.info("Empty response Children List : {}", responseChildrenList);

        // 연결된 어린이 리스트에 값이 있다면
        if (childrenList != null && !childrenList.isEmpty()) {
            responseChildrenList = new ArrayList<>();
            for (User child : childrenList) {
                //반환 형식에 맞게 dto에 담기
                ConnectedUserInfoDto responseChild = ConnectedUserInfoDto.builder()
                        .name(child.getName())
                        .nickname(child.getNickname())
                        .build();
                // 리스트에 넣기
                responseChildrenList.add(responseChild);
            }
        }

        log.info("response Children List : {}", responseChildrenList);

        // data에 map형식으로 담기
        Map<String, Object> data = new HashMap<>();
        data.put("children", responseChildrenList);

        //response
        ResponseDto response = ResponseDto.builder()
                .message("연결 어린이 조회에 성공했습니다.")
                .data(data)
                .build();
        return response;
    }
}
