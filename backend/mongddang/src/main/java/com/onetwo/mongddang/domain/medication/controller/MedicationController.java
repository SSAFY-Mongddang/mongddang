package com.onetwo.mongddang.domain.medication.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.medication.dto.RequestDeleteMedicationDto;
import com.onetwo.mongddang.domain.medication.dto.RequestMedicationDto;
import com.onetwo.mongddang.domain.medication.service.MedicationService;
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
@RequestMapping("/api/medication")
@Tag(name = "Medication API", description = "약 api")
public class MedicationController {

    private final MedicationService medicationService;
    private final JwtExtratService jwtExtratService;

    // 복약 등록하기
    @PostMapping("register")
    @ChildRequired
    @Tag(name = "Medication API", description = "약 api")
    @Operation(summary = "복약 등록하기", description = "약을 등록합니다.")
    public ResponseEntity<ResponseDto> registerMedication(
            @RequestBody RequestMedicationDto requestMedicationDto,
            HttpServletRequest request) {
        log.info("POST /api/medication/register");

        Long userId = jwtExtratService.jwtFindId(request);

        ResponseDto responseDto = medicationService.registerMedication(userId, requestMedicationDto);
        return ResponseEntity.ok(responseDto);
    }


    // 등록한 약품 조회하기
    @GetMapping("")
    @Tag(name = "Medication API", description = "약 api")
    @Operation(summary = "등록한 약품 조회하기", description = "등록한 약품을 조회합니다.")
    public ResponseEntity<ResponseDto> getMedication(@RequestParam String nickname, HttpServletRequest request) {
        log.info("GET /api/medication");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = medicationService.getMedication(userId, nickname);
        return ResponseEntity.ok(responseDto);
    }

    // 등록한 약품 관리 삭제하기
    @DeleteMapping("delete")
    @Tag(name = "Medication API", description = "약 api")
    @Operation(summary = "등록한 약품 관리 삭제하기", description = "등록한 약품 관리를 삭제합니다.")
    public ResponseEntity<ResponseDto> deleteMedication(
            @RequestBody RequestDeleteMedicationDto requestDto,
            HttpServletRequest request) {
        log.info("DELETE /api/medication/delete");

        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = medicationService.deleteMedication(requestDto.getNickname(), requestDto.getMedicationManagementId(), userId);
        return ResponseEntity.ok(responseDto);
    }

}
