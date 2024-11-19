package com.onetwo.mongddang.domain.fcm.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.dto.DeviceTokenRegisterDto;
import com.onetwo.mongddang.domain.fcm.service.DeviceTokenService;
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
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
@Tag(name = "Fcm API", description = "Fcm 알림 기능 api")
public class FcmController {

    private final JwtExtratService jwtExtratService;
    private final DeviceTokenService deviceTokenService;

    @PostMapping("/token")
    @Operation(summary = "fcm device token 저장", description = "알림을 전송할 디바이스의 토큰값을 저장합니다.")
    public ResponseEntity<ResponseDto> getDeviceToken(@RequestBody DeviceTokenRegisterDto deviceTokenRegisterDto, HttpServletRequest request) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = deviceTokenService.saveOrUpdateToken(deviceTokenRegisterDto.getToken(), userId);
        return ResponseEntity.ok(responseDto);
    }
}
