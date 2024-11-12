package com.onetwo.mongddang.domain.fcm.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.dto.DeviceTokenRegisterDto;
import com.onetwo.mongddang.domain.fcm.dto.RequestReadNotiDto;
import com.onetwo.mongddang.domain.fcm.service.GetPushLogService;
import com.onetwo.mongddang.domain.fcm.service.ReadPushLogService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/push")
@RequiredArgsConstructor
@Tag(name = "Push API", description = "push 알림 관련 api")
public class NotificationController {

    private final JwtExtratService jwtExtratService;
    private final GetPushLogService getPushLogService;
    private final ReadPushLogService readPushLogService;

    @GetMapping("/log")
    @Operation(summary = "Push Log 조회", description = "해당 사용자의 알림 로그를 조회합니다. 지정한 페이지, 사이즈로 페이지네이션 처리하여 전달합니다.")
    public ResponseEntity<ResponseDto> getDeviceToken(HttpServletRequest request,
                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = getPushLogService.GetPushLog(userId,page,size);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/read")
    @Operation(summary = "Push Log 읽음 처리", description = "Push Log를 읽음처리합니다.")
    public ResponseEntity<ResponseDto> getDeviceToken(HttpServletRequest request,
                                                      @Valid @RequestBody RequestReadNotiDto requestReadNotiDto) {
        Long userId = jwtExtratService.jwtFindId(request);
        ResponseDto responseDto = readPushLogService.readPushLog(userId,requestReadNotiDto.getNotificationId());
        return ResponseEntity.ok(responseDto);
    }

}
