package com.onetwo.mongddang.domain.game.achievement.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.achievement.dto.RequestClaimAchievementDto;
import com.onetwo.mongddang.domain.game.achievement.service.AchievementService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game/collection/achievement")
@Tag(name = "Achievement API", description = "업적 API")
public class AchievementController {

    final AchievementService achievementService;
    final JwtExtratService jwtExtratService;

    // 업적 목록 조회 api
    @GetMapping("")
    @ChildRequired
    @Transactional
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "업적 목록 조회 api", description = "업적 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getAchievementList(HttpServletRequest request) {
        log.info("GET /api/game/collection/achievement");

        Long id = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = achievementService.getAchievementList(id);
        return ResponseEntity.ok(responseDto);
    }

    // 업적 보상 수령
    @PostMapping("/claim")
    @ChildRequired
    @Transactional
    @Tag(name = "Collection API", description = "도감 api")
    @Operation(summary = "업적 보상 수령", description = "업적 보상을 수령합니다.")
    public ResponseEntity<ResponseDto> claimAchievement(@RequestBody RequestClaimAchievementDto requestDto, HttpServletRequest request) {
        log.info("GET /api/game/collection/achievement/claim");

        Long id = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = achievementService.claimAchievementReward(id, requestDto.getAchievementId());
        return ResponseEntity.ok(responseDto);
    }

}
