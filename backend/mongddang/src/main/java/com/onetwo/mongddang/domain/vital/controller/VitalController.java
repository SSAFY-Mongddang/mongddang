package com.onetwo.mongddang.domain.vital.controller;

import com.onetwo.mongddang.common.ResponseDto;
import com.onetwo.mongddang.domain.vital.model.Vital;
import com.onetwo.mongddang.domain.vital.service.SearchVitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/bloodsugar")
@Tag(name="vital 기록 조회 api", description = "해당 환아의 혈당을 조회합니다.")
public class VitalController {
    private final SearchVitalService searchVitalService;

    @Operation(summary = "현재 혈당조회 api", description = "url의 nickname의 환아의 현재 혈당을 조회할 수 있습니다.")
    @GetMapping("/{nickname}")
    public void getCurrentGlucose(@PathVariable("nickname") String nickname, HttpServletRequest request){
        log.info("GET /api/bloodsugar/{nickname}");
        LocalDateTime currentTime = LocalDateTime.now();
        Long userId = 1L;
    }

    @Operation(summary = "일일 혈당조회 api", description = "해당 날짜의 하루치 혈당기록을 조회할 수 있습니다.")
    @GetMapping("/{nickname}/{date}")
    public ResponseEntity<ResponseDto> getThisdateGlucose( @PathVariable("nickname") String nickname,@PathVariable("date") String date, HttpServletRequest request){
        log.info("GET /api/bloodsugar/{nickname}/{date}");
        Long userId = 1L;
        ResponseDto response = searchVitalService.SearchThisDayVital(userId, date);
        return ResponseEntity.ok(response);
    }
}
