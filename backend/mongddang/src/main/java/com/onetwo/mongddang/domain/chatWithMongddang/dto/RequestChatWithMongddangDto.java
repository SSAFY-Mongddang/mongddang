package com.onetwo.mongddang.domain.chatWithMongddang.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestChatWithMongddangDto {

    @NotNull(message = "message 는 필수값입니다.")
    private String message;

}
