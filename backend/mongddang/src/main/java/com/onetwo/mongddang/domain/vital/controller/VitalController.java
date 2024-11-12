package com.onetwo.mongddang.domain.vital.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import com.onetwo.mongddang.domain.vital.service.VitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bloodsugar")
@Tag(name = "Vital API", description = "혈당 api")
public class VitalController {

    private final VitalService vitalService;
    private final JwtExtratService jwtExtratService;

    // 혈당 기록 조회
    @GetMapping("")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "혈당 기록 조회", description = "혈당 기록을 조회합니다.")
    public ResponseEntity<ResponseDto> getBloodSugar(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            @RequestParam LocalDate date,
            HttpServletRequest request
    ) {
        log.info("GET /api/bloodsugar?nickname={}&startDate={}&endDate={}\", nickname, startDate, endDate");

        Long userId = jwtExtratService.jwtFindId(request);

        // 서비스 작성
        ResponseDto responseDto = vitalService.getBloodSugar(userId, nickname, date);
        return ResponseEntity.ok(responseDto);

    }

    // 현재 혈당 조회
    @PostMapping("/current")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "현재 혈당 조회", description = "현재 혈당을 조회합니다.")
    public ResponseEntity<ResponseDto> getCurrentBloodSugar(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            HttpServletRequest request
    ) {
        log.info("GET /api/bloodsugar/current?nickname={}\", nickname");

        Long userId = jwtExtratService.jwtFindId(request);

        // 서비스 작성
        ResponseDto responseDto = vitalService.getCurrentBloodSugar(userId, nickname);
        return ResponseEntity.ok(responseDto);
    }


    // 리포트 조회
    @GetMapping("/report")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "리포트 조회", description = "리포트를 조회합니다.")
    public ResponseEntity<ResponseDto> getReport(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            HttpServletRequest request
    ) {
        log.info("GET /api/bloodsugar/report?nickname={}&startDate={}&endDate={}\", nickname, startDate, endDate");

        Long userId = jwtExtratService.jwtFindId(request);

        // 서비스 작성
        ResponseDto responseDto = vitalService.getReport(userId, nickname, startDate, endDate);
        return ResponseEntity.ok(responseDto);
    }


}
