package com.onetwo.mongddang.domain.record.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.medication.dto.RequestMedicationCheckDto;
import com.onetwo.mongddang.domain.record.service.RecordMedicationService;
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
@RequestMapping("/api/record")
@Tag(name = "Record API", description = "기록 api")
public class RecordMedicationController {

    private final RecordMedicationService recordMedicationService;
    private final JwtExtratService jwtExtratService;


    // 복약 확인하기
    @PostMapping("/medication/check")
    @ChildRequired
    @Tag(name = "Record API", description = "복약 기록 api")
    @Operation(summary = "복약 확인하기", description = "복약을 확인합니다.")
    public ResponseEntity<ResponseDto> checkMedication(
            @RequestBody RequestMedicationCheckDto requestMedicationCheckDto,
            HttpServletRequest request) throws JsonProcessingException {
        log.info("POST /api/record/medication/check");

        Long childId = jwtExtratService.jwtFindId(request);

        // 서비스 호출
        ResponseDto responseDto = recordMedicationService.checkMedication(childId, requestMedicationCheckDto.getMedicationId());
        return ResponseEntity.ok(responseDto);
    }

}
