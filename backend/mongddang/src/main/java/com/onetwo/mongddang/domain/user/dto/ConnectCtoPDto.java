package com.onetwo.mongddang.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ConnectCtoPDto {

    @NotBlank(message = "이 필드는 비어있을 수 없습니다.")
    private String invitationCode;
}
