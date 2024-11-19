package com.onetwo.mongddang.domain.record.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.record.dto.meal.RequestMealStartDto;
import com.onetwo.mongddang.domain.record.service.RecordMealService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
@Tag(name = "Record API", description = "기록 api")
public class RecordMealController {

    private final RecordMealService recordMealService;
    private final JwtExtratService jwtExtratService;

    // 식사 시작하기 api
    @PostMapping("/meal/start")
    @ChildRequired
    @Tag(name = "Record API", description = "식사 기록 api")
    @Operation(summary = "식사 시작하기 api", description = "식사를 시작합니다.")
    public ResponseEntity<ResponseDto> startMeal(
            @RequestBody RequestMealStartDto requestMealStartDto,
//            @RequestParam("content") String contentJson,  // JSON 문자열로 받기
//            @RequestParam(value = "image", required = false) MultipartFile imageFile,
//            @RequestParam("mealTime") @NotNull(message = "식사 시간은 필수입니다.")
//            @Pattern(regexp = "^(breakfast|lunch|dinner|snack)$", message = "식사 시간은 'breakfast', 'lunch', 'dinner', 'snack' 중 하나여야 합니다.") @NotNull String mealTime,
            HttpServletRequest request) {

        log.info("POST /api/record/meal/start");

        Long childId = jwtExtratService.jwtFindId(request);

        // 서비스 호출
        ResponseDto responseDto = recordMealService.startMeal(childId, requestMealStartDto.getContent(), requestMealStartDto.getImage(), requestMealStartDto.getMealTime());
        return ResponseEntity.ok(responseDto);
    }

    // 식사 종료하기 api
    @PatchMapping("/meal/end")
    @ChildRequired
    @Tag(name = "Record API", description = "식사 기록 api")
    @Operation(summary = "식사 종료하기 api", description = "식사를 종료합니다.")
    public ResponseEntity<ResponseDto> endMeal(HttpServletRequest request) {
        log.info("POST /api/record/meal/end");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordMealService.endMeal(childId);
        return ResponseEntity.ok(responseDto);
    }


    // 식사 수정하기 api
    @PatchMapping("/meal/edit")
    @ChildRequired
    @Tag(name = "Record API", description = "식사 기록 api")
    @Operation(summary = "식사 수정하기 api", description = "식사를 수정합니다.")
    public ResponseEntity<ResponseDto> editMeal(
            @RequestParam("recordId") Long recordId,
            @RequestParam("content") String contentJson,  // JSON 문자열로 받기
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("mealTime") @NotNull(message = "식사 시간은 필수입니다.")
            @Pattern(regexp = "^(breakfast|lunch|dinner|snack)$", message = "식사 시간은 'breakfast', 'lunch', 'dinner', 'snack' 중 하나여야 합니다.") String mealTime,
            HttpServletRequest request) {
        log.info("POST /api/record/meal/edit");

        Long childId = jwtExtratService.jwtFindId(request);

        // 서비스 호출
        ResponseDto responseDto = recordMealService.editMeal(childId, recordId, contentJson, image, mealTime);
        return ResponseEntity.ok(responseDto);
    }


}
