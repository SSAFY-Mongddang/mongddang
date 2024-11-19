package com.onetwo.mongddang.domain.fcm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeviceTokenRegisterDto {

    @NotBlank(message = "fcm 토큰 값은 필수 필드입니다.")
    private String token;
}
