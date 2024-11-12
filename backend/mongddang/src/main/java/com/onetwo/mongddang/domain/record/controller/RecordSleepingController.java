package com.onetwo.mongddang.domain.record.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.record.service.RecordSleepingService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
@Tag(name = "Record API", description = "기록 api")
public class RecordSleepingController {

    private final RecordSleepingService recordSleepingService;
    private final JwtExtratService jwtExtratService;

    // 수면 시작하기 api
    @PostMapping("/sleep/start")
    @ChildRequired
    @Tag(name = "Record API", description = "수면 기록 api")
    @Operation(summary = "수면 시작하기 api", description = "수면을 시작합니다.")
    public ResponseEntity<ResponseDto> startSleep(HttpServletRequest request) {
        log.info("POST /api/record/sleep/start");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordSleepingService.startSleep(childId);
        return ResponseEntity.ok(responseDto);
    }


    // 수면 종료하기 api
    @PatchMapping("/sleep/end")
    @ChildRequired
    @Tag(name = "Record API", description = "수면 기록 api")
    @Operation(summary = "수면 종료하기 api", description = "수면을 종료합니다.")
    public ResponseEntity<ResponseDto> endSleep(HttpServletRequest request) {
        log.info("Patch /api/record/sleep/end");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordSleepingService.endSleep(childId);
        return ResponseEntity.ok(responseDto);
    }


}
