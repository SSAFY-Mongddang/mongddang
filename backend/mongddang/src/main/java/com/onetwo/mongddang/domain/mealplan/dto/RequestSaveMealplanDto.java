package com.onetwo.mongddang.domain.mealplan.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSaveMealplanDto {

    @NotBlank(message = "닉네임은 필수값입니다.")
    private String nickname;

    @NotNull(message = "저장할 식단을 입력해주세요.")
    private List<JsonNode> mealList;

}
