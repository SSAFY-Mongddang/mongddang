package com.onetwo.mongddang.domain.fcm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestReadNotificationDto {

    @NotNull(message = "알림 id는 필수 필드입니다.")
    private Long notificationId;
}
