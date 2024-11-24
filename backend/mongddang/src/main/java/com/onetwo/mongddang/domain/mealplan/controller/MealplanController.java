package com.onetwo.mongddang.domain.mealplan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestMealInfoDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestSaveMealplanDto;
import com.onetwo.mongddang.domain.mealplan.service.GetMealplanIsExistService;
import com.onetwo.mongddang.domain.mealplan.service.GetMealplanService;
import com.onetwo.mongddang.domain.mealplan.service.MealplanService;
import com.onetwo.mongddang.domain.mealplan.service.SaveMealService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/meal-data")
@RequiredArgsConstructor
@Tag(name = "급식 API", description = "급식정보를 받아오는 API")
public class MealplanController {

    private final JwtExtratService jwtExtratService;
    private final MealplanService mealplanService;
    private final SaveMealService saveMealService;
    private final GetMealplanService getMealplanService;
    private final GetMealplanIsExistService getMealplanIsExistService;

    @PostMapping("/get")
    @Operation(summary = "급식 받아오기 API", description = "어린이의 닉네임, 학교명, 해당 월, 조중석식을 선택하면 식단을 검색하여 반환합니다.")
    public ResponseEntity<ResponseDto> getMealApi(@Valid @RequestBody RequestMealInfoDto requestMealInfoDto, HttpServletRequest request)
            throws JsonProcessingException {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto response = mealplanService.getMealplan(requestMealInfoDto, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @Operation(summary = "급식 저장하기 API", description = "사용자가 확인한 식단을 저장합니다.")
    public ResponseEntity<ResponseDto> saveMealInfo(@Valid @RequestBody RequestSaveMealplanDto requestSaveMealplanDto, HttpServletRequest request) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto response = saveMealService.saveMealplan(requestSaveMealplanDto, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    @Operation(summary = "급식 조회하기 API", description = "사용자가 저장한 식단 중 오늘의 식단을 조회합니다.")
    public ResponseEntity<ResponseDto> getMealInfo(HttpServletRequest request,
                                                   @RequestParam String mealTime,
                                                   @RequestParam String nickname) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto response = getMealplanService.getTodayMealplan(userId, mealTime,nickname);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    @Operation(summary = "급식 여부 판별 API", description = "사용자가 저장한 식단이 있는지 조회합니다.")
    public ResponseEntity<ResponseDto> getMealplanIsExist(HttpServletRequest request,
                                                   @RequestParam String nickname) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto response = getMealplanIsExistService.getMealplanIsExist(userId,nickname);
        return ResponseEntity.ok(response);
    }
}
