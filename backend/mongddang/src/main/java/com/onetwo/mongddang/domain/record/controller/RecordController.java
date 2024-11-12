package com.onetwo.mongddang.domain.record.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.record.service.RecordService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
@Tag(name = "Record API", description = "기록 api")
public class RecordController {

    private final RecordService recordService;
    private final JwtExtratService jwtExtratService;

    // 환아의 활동 기록 조회
    @GetMapping("")
    @Tag(name = "Record API", description = "기록 api")
    @Operation(summary = "환아의 활동 기록 조회", description = "환아의 활동 기록을 조회합니다.")
    public ResponseEntity<ResponseDto> getRecord(
            @NotBlank(message = "닉네임은 필수입니다.") @RequestParam String nickname,
            @NotBlank(message = "시작 날짜는 필수입니다.") @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$", message = "시작 날짜는 YYYY-MM 형식이어야 하며, 월은 01부터 12 사이여야 합니다.") @RequestParam String startDate,
            @NotBlank(message = "종료 날짜는 필수입니다.")
            @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$", message = "종료 날짜는 YYYY-MM 형식이어야 하며, 월은 01부터 12 사이여야 합니다.") @RequestParam String endDate,
            HttpServletRequest request
    ) {
        log.info("GET /api/record?nickname={}&startDate={}&endDate={}", nickname, startDate, endDate);

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = recordService.getRecord(userId, nickname, startDate, endDate);
        return ResponseEntity.ok(responseDto);
    }

    // 진행 중인 활동 찾기 api
    @GetMapping("ongoing")
    @ChildRequired
    @Tag(name = "Record API", description = "기록 api")
    @Operation(summary = "진행 중인 식사 찾기 api", description = "진행 중인 식사를 찾습니다.")
    public ResponseEntity<ResponseDto> findMeal(HttpServletRequest request) {
        log.info("GET /api/record/meal/ongoing");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordService.findOngoingRecord(childId);
        return ResponseEntity.ok(responseDto);
    }
}
