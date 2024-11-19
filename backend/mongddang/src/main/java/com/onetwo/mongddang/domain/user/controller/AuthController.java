package com.onetwo.mongddang.domain.user.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.user.dto.CheckNicknameRequestDto;
import com.onetwo.mongddang.domain.user.dto.SignupRequestDto;
import com.onetwo.mongddang.domain.user.service.CheckNicknameService;
import com.onetwo.mongddang.domain.user.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "Signup API", description = "회원가입 API")
public class AuthController {

    private final SignupService signupService;
    private final CheckNicknameService checkNicknameService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "비회원 유저에 대해 회원가입을 진행합니다.")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        ResponseDto response = signupService.signup(signupRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-nickname")
    @Operation(summary = "닉네임 중복검사 API", description = "닉네임 중복 및 유효성을 검사합니다.")
    public ResponseEntity<ResponseDto> checknickname(@Valid @RequestBody CheckNicknameRequestDto checkNicknameRequestDto){
        ResponseDto response = checkNicknameService.checkNickname(checkNicknameRequestDto);
        return ResponseEntity.ok(response);
    }
}
