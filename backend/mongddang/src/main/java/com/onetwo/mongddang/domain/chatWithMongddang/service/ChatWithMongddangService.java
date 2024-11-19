package com.onetwo.mongddang.domain.chatWithMongddang.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.utils.GptUtils;
import com.onetwo.mongddang.domain.chatWithMongddang.prompt.PromptConstants;
import com.onetwo.mongddang.domain.game.mongddang.errors.CustomMongddangErrorCode;
import com.onetwo.mongddang.domain.game.mongddang.model.MyMongddang;
import com.onetwo.mongddang.domain.game.mongddang.repository.MyMongddangRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatWithMongddangService {

    private final PromptConstants promptConstants;
    private final GptUtils gptUtils;
    private final MyMongddangRepository myMongddangRepository;


    public ResponseDto startChatWithMongddang(Long userId, String message) throws JsonProcessingException {
        log.info("userId: {}", userId);

        MyMongddang mongddang = myMongddangRepository.findByChildIdAndIsMainTrue(userId)
                .orElseThrow(() -> new RestApiException(CustomMongddangErrorCode.CHARACTER_NOT_OWNED));

        // 몽땅과의 채팅을 새롭게 시작할 때
        String startPrompt = promptConstants.getPrompt(mongddang.getMongddang().getId());
        String gptMessage = gptUtils.requestGpt(startPrompt + message, 1.0f);

        Map<String, Object> response = new HashMap<>();
        response.put("prompt", startPrompt);
        response.put("message", gptMessage);

        return ResponseDto.builder()
                .message("몽땅과의 새로운 대화를 시작합니다.")
                .data(response)
                .build();
    }


    public ResponseDto chatWithMongddang(Long userId, String message) throws JsonProcessingException {
        log.info("userId: {}", userId);

        myMongddangRepository.findByChildIdAndIsMainTrue(userId)
                .orElseThrow(() -> new RestApiException(CustomMongddangErrorCode.CHARACTER_NOT_OWNED));

        // 몽땅과 기존의 채팅을 바탕으로 진행
        return ResponseDto.builder()
                .message("몽땅과 기존의 대화를 진행합니다.")
                .data(gptUtils.requestGpt(message, 1.0f))
                .build();
    }


}
