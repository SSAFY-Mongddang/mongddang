package com.onetwo.mongddang.domain.user.dto;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "이 필드는 비어있을 수 없습니다.")
    private String idToken;

    @NotNull(message = "이 필드는 비어있을 수 없습니다.")
    private User.Role role;

    @NotNull(message = "이 필드는 비어있을 수 없습니다.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;

    @NotBlank(message = "이 필드는 비어있을 수 없습니다.")
    private String name;

    @NotBlank(message = "이 필드는 비어있을 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9\\sㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$", message = "형식이 올바르지 않습니다.")
    private String nickname;

    @NotNull(message = "이 필드는 비어있을 수 없습니다.")
    private User.Gender gender;
}
