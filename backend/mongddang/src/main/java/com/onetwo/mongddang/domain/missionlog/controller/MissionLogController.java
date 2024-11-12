package com.onetwo.mongddang.domain.missionlog.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.missionlog.service.MissionLogService;
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
@RequestMapping("/api/mission")
@Tag(name = "Mission API", description = "미션 API")
public class MissionLogController {

    private final JwtExtratService jwtExtratService;
    private final MissionLogService missionLogService;
    private final MissionLogUtils missionLogUtils;

    // 미션 목록 조회 api
    @GetMapping("")
    @ChildRequired
    @Tag(name = "Mission API", description = "미션 api")
    @Operation(summary = "미션 목록 조회 api", description = "미션 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getMissionList(HttpServletRequest request) {
        log.info("GET /api/mission");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = missionLogService.getTodayMissionLogList(childId);
        return ResponseEntity.ok(responseDto);
    }

    // 미션 보상 수령 api
    @PostMapping("reward")
    @ChildRequired
    @Tag(name = "Mission API", description = "미션 api")
    @Operation(summary = "미션 보상 수령 api", description = "미션 보상을 수령합니다.")
    public ResponseEntity<ResponseDto> rewardMission(@RequestParam Long missionId, HttpServletRequest request) {
        log.info("POST /api/mission");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = missionLogService.rewardMission(missionId, childId);
        return ResponseEntity.ok(responseDto);
    }

}
