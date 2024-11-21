package com.onetwo.mongddang.domain.vital.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import com.onetwo.mongddang.domain.vital.dto.BloodGlucoseRequest;
import com.onetwo.mongddang.domain.vital.service.VitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vital")
@Tag(name = "Vital API", description = "혈당 api")
public class VitalController {

    private final VitalService vitalService;
    private final JwtExtratService jwtExtratService;

    // 일일 혈당 기록 조회
    @GetMapping("bloodsugar")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "일일 혈당 기록 조회", description = "혈당 기록을 조회합니다.")
    public ResponseEntity<ResponseDto> getBloodSugar(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            @RequestParam LocalDate date,
            HttpServletRequest request
    ) {
        log.info("GET /api/bloodsugar?nickname={}&startDate={}&endDate={}\", nickname, startDate, endDate");

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = vitalService.getBloodSugar(userId, nickname, date);
        return ResponseEntity.ok(responseDto);

    }

    // 현재 혈당 조회
    @PostMapping("bloodsugar/current")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "현재 혈당 조회", description = "현재 혈당을 조회합니다.")
    public ResponseEntity<ResponseDto> getCurrentBloodSugar(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            HttpServletRequest request
    ) {
        log.info("GET /api/bloodsugar/current?nickname={}\", nickname");

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = vitalService.getCurrentBloodSugar(userId, nickname);
        return ResponseEntity.ok(responseDto);
    }


    // 리포트 조회
    @GetMapping("bloodsugar/report")
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

        ResponseDto responseDto = vitalService.getReport(userId, nickname, startDate, endDate);
        return ResponseEntity.ok(responseDto);
    }


    // 리포트 GPT 요약 생성
    @PostMapping("bloodsugar/report/gpt")
    @Tag(name = "Vital API", description = "혈당 api")
    @Operation(summary = "리포트 GPT 요약 생성", description = "리포트 GPT 요약을 생성합니다.")
    public ResponseEntity<ResponseDto> getGptSummary(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            @RequestBody String message,
            HttpServletRequest request
    ) {
        log.info("POST /api/bloodsugar/report/gpt?nickname={}&startDate={}&endDate={}\", nickname, startDate, endDate");

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = vitalService.getGptSummary(userId, nickname, message);
        return ResponseEntity.ok(responseDto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("bloodsugar/test")
    @Operation(summary = "삼성 헬스 데이터 테스트 용입니다.", description = "API 호출 테스트")
    public ResponseEntity<String> getTest() {
        try {
            String valueTest = "테스트 성공";
            log.info("GET /api/bloodgar/test, valueTest={}", valueTest);
            return ResponseEntity.ok(valueTest);
        } catch (Exception e) {
            log.error("Error in getTest", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("테스트 실패");
        }
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("bloodsugar/current/send")
    @Operation(summary = "삼성 헬스 데이터 받는 용입니다.", description = "API 5분 모니터링용")
    public ResponseEntity<ResponseDto> getBloodGlucose(@RequestBody BloodGlucoseRequest dto,  HttpServletRequest request) {
        System.out.println("BloodSugarLevel: " + dto.getBloodSugarLevel());
        System.out.println("MeasurementTime: " + dto.getMeasurementTime());
        Long userId = jwtExtratService.jwtFindId(request);
        // 요청 처리 로직 (예: 데이터 저장, 상태 계산 등)
        if (dto.getBloodSugarLevel() != null) {
            // 상태 계산 로직 (추가 필요 시)
            if (dto.getBloodSugarLevel() < 70) {
                System.out.println("Status: Low");
            } else if (dto.getBloodSugarLevel() > 140) {
                System.out.println("Status: High");
            } else {
                System.out.println("Status: Normal");
            }
        }
        ResponseDto responseDto = vitalService.getSamsung(userId, dto);
        return ResponseEntity.ok(responseDto);
    }

}
