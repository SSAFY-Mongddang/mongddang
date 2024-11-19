package com.onetwo.mongddang.domain.chatWithMongddang.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.chatWithMongddang.dto.RequestChatWithMongddangDto;
import com.onetwo.mongddang.domain.chatWithMongddang.service.ChatWithMongddangService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/mongddang")
@Tag(name = "ChatWithMongddang API", description = "몽땅과의 채팅 api")
public class ChatWithMongddangController {


    private final ChatWithMongddangService chatWithMongddangService;
    private final JwtExtratService jwtExtratService;


    @PostMapping("start")
    @ChildRequired
    @Tag(name = "ChatWithMongddang API", description = "몽땅과의 대화 api")
    @Operation(summary = "몽땅과의 대화", description = "몽땅과 대화를 시작 합니다.")
    public ResponseEntity<ResponseDto> startChatWithMongddang(@RequestBody RequestChatWithMongddangDto requestChatWithMongddangDto, HttpServletRequest request) throws JsonProcessingException {
        log.info("requestChatWithMongddangDto: {}", requestChatWithMongddangDto);

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = chatWithMongddangService.startChatWithMongddang(userId, requestChatWithMongddangDto.getMessage());
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("")
    @ChildRequired
    @Tag(name = "ChatWithMongddang API", description = "몽땅과의 대화 api")
    @Operation(summary = "몽땅과의 대화", description = "몽땅과 대화를 합니다.")
    public ResponseEntity<ResponseDto> getPrompt(@RequestBody RequestChatWithMongddangDto requestChatWithMongddangDto, HttpServletRequest request) throws JsonProcessingException {
        log.info("requestChatWithMongddangDto: {}", requestChatWithMongddangDto);

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = chatWithMongddangService.chatWithMongddang(userId, requestChatWithMongddangDto.getMessage());
        return ResponseEntity.ok(responseDto);
    }

}
