package com.onetwo.mongddang.domain.mealplan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestMealInfoDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestSaveMealplanDto;
import com.onetwo.mongddang.domain.mealplan.service.MealplanService;
import com.onetwo.mongddang.domain.mealplan.service.SaveMealService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/meal-data")
@RequiredArgsConstructor
@Tag(name = "급식 API", description = "급식정보를 받아오는 API")
public class MealplanController {

    private final JwtExtratService jwtExtratService;
    private final MealplanService mealplanService;
    private final SaveMealService saveMealService;

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
}
