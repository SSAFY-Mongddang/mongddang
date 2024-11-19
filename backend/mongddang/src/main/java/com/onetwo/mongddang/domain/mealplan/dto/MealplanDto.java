package com.onetwo.mongddang.domain.mealplan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// get에서는 response, save는 request로 동작하는 dto입니다.

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MealplanDto {

    private String school;
    private String mealTime;
    private LocalDate date;
    private List<String> meal;

}
