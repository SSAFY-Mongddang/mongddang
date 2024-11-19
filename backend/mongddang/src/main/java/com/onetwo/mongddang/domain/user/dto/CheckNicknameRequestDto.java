package com.onetwo.mongddang.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CheckNicknameRequestDto {

    @NotBlank(message = "이 필드는 비어있을 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9\\sㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$", message = "형식이 올바르지 않습니다.")
    private String nickname;
}
