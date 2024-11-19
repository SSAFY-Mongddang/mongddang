package com.onetwo.mongddang.domain.game.mongddang.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.mongddang.dto.RequestMongddangIdDto;
import com.onetwo.mongddang.domain.game.mongddang.service.MongddangService;
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
@RequestMapping("/api/game/collection/mongddang")
@Tag(name = "Collection API", description = "도감 api")
public class MongddangController {

    private final MongddangService mongddangService;
    private final JwtExtratService jwtExtratService;

    // 몽땅 목록 조회 api
    @GetMapping("")
    @ChildRequired
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "몽땅 목록 조회 api", description = "몽땅 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getMongddangList(HttpServletRequest request) {
        log.info("GET /api/game/collection/mongddang");

        Long id = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = mongddangService.getMongddangList(id);
        return ResponseEntity.ok(responseDto);
    }

    // 몽땅 모집 api
    @PostMapping("/recruitment")
    @ChildRequired
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "몽땅 모집 api", description = "코인을 지불하여 몽땅을 모집합니다.")
    public ResponseEntity<ResponseDto> recruitMongddang(
            @RequestBody RequestMongddangIdDto requestDto,
            HttpServletRequest request) {
        log.info("POST /api/game/collection/mongddang/recruitment");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = mongddangService.recruitmentMongddang(userId, requestDto.getMongddangId());
        return ResponseEntity.ok(responseDto);
    }


    // 새로운 몽땅 표시 제거
    @PatchMapping("/check")
    @ChildRequired
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "새로운 몽땅 표시 제거", description = "새로운 몽땅 표시를 제거합니다.")
    public ResponseEntity<ResponseDto> checkNewMongddang(@RequestBody RequestMongddangIdDto requestDto, HttpServletRequest request) {
        log.info("PUT /api/game/collection/mongddang/check");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = mongddangService.checkNewMongddang(requestDto.getMongddangId(), userId);
        return ResponseEntity.ok(responseDto);
    }

    // 몽땅 메인 설정
    @PatchMapping("/main")
    @ChildRequired
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "몽땅 메인 설정", description = "몽땅을 메인으로 설정합니다.")
    public ResponseEntity<ResponseDto> setMainMongddang(@RequestBody RequestMongddangIdDto requestDto, HttpServletRequest request) {
        log.info("PUT /api/game/collection/mongddang/main");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = mongddangService.setMainMongddang(requestDto.getMongddangId(), userId);
        return ResponseEntity.ok(responseDto);
    }
}