package com.onetwo.mongddang.domain.record.controller;

import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.record.service.RecordExerciseService;
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
public class RecordExerciseController {

    private final RecordExerciseService recordExerciseService;
    private final JwtExtratService jwtExtratService;

    // 운동 시작하기 api
    @PostMapping("/exercise/start")
    @ChildRequired
    @Tag(name = "Record API", description = "운동 기록 api")
    @Operation(summary = "운동 시작하기 api", description = "운동을 시작합니다.")
    public ResponseEntity<ResponseDto> startExercise(HttpServletRequest request) {
        log.info("POST /api/record/exercise/start");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordExerciseService.startExercise(childId);
        return ResponseEntity.ok(responseDto);
    }


    // 운동 종료하기 api
    @PatchMapping("/exercise/end")
    @ChildRequired
    @Tag(name = "Record API", description = "운동 기록 api")
    @Operation(summary = "운동 종료하기 api", description = "운동을 종료합니다.")
    public ResponseEntity<ResponseDto> endExercise(HttpServletRequest request) {
        log.info("POST /api/record/exercise/end");

        Long childId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = recordExerciseService.endExercise(childId);
        return ResponseEntity.ok(responseDto);
    }


}
