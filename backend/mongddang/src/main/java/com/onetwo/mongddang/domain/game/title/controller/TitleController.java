package com.onetwo.mongddang.domain.game.title.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.title.dto.RequestTitleIdDto;
import com.onetwo.mongddang.domain.game.title.service.TitleService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/collection/title")
@Tag(name = "Collection API", description = "도감 api")
public class TitleController {

    private final TitleService titleService;
    private final JwtExtratService jwtExtratService;

    // 메인 칭호 설정 api
    @PatchMapping("/main")
    @ChildRequired
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "메인 칭호 설정 api", description = "메인 칭호를 설정합니다.")
    public ResponseEntity<ResponseDto> setMainTitle(@RequestBody RequestTitleIdDto requestDto, HttpServletRequest request) {
        log.info("Put /api/game/collection/title/main");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = titleService.setMainTitle(userId, requestDto.getTitleId());
        return ResponseEntity.ok(responseDto);
    }


}
