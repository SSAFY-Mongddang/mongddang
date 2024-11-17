package com.onetwo.mongddang.domain.chatWithMongddang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.chatWithMongddang.dto.RequestAudioDto;
import com.onetwo.mongddang.domain.chatWithMongddang.service.TalkWithMongddangService;
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
@Tag(name = "TalkWithMongddang API", description = "몽땅과의 대화 api")
public class TalkWithMongddangController {

    private final JwtExtratService jwtExtratService;
    private final TalkWithMongddangService talkWithMongddangService;


    @PostMapping("talk")
    @ChildRequired
    @Tag(name = "TalkWithMongddang API", description = "몽땅과의 대화 api")
    @Operation(summary = "몽땅과의 대화", description = "몽땅과 대화를 시작 합니다.")
    public ResponseEntity<ResponseDto> talkWithMongddang(
            @RequestBody RequestAudioDto requestAudioDto,
//            @RequestBody RequestAudioDto requestAudioDto,
//            @RequestParam(value = "audioFile") MultipartFile audioFile,
            HttpServletRequest request
    ) throws JsonProcessingException {
//        log.info("audioFile: {}", audioFile);
        Long userId = jwtExtratService.jwtFindId(request);
        log.info("userId: {}", userId);

        ResponseDto responseDto = talkWithMongddangService.talkWithMongddang(userId, requestAudioDto.getAudio());
        return ResponseEntity.ok(responseDto);
    }

}
