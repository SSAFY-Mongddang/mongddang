package com.onetwo.mongddang.domain.mealplan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMealInfoDto {

    @NotBlank(message = "학교명은 필수값입니다.")
    private String schoolName;

    @NotNull(message = "급식 시간은 필수값입니다.")
    private MealType mealTime;

    @NotNull(message = "급식 연월은 필수값입니다.")
    @JsonFormat(pattern = "yyyyMM")
    private YearMonth month;

    private String nickname;

    public enum MealType {
        breakfast, lunch, dinner
    }

    ;
}
