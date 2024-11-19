package com.onetwo.mongddang.domain.user.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import com.onetwo.mongddang.domain.user.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/main")
@RequiredArgsConstructor
@Tag(name = "Main API", description = "메인화면 용 어린이 게임 정보")
public class MainController {

    private final JwtExtratService jwtExtratService;
    private final MainService mainService;

    @GetMapping("")
    @Operation(summary = "메인 화면 API", description = "메인화면 용 어린이 게임 정보를 조회합니다.")
    public ResponseEntity<ResponseDto> mainpageInfo(HttpServletRequest request) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto response = mainService.getMainInfo(userId);
        return ResponseEntity.ok(response);
    }
}
