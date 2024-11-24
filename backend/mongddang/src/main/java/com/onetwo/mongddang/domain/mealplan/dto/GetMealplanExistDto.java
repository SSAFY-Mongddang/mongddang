package com.onetwo.mongddang.domain.mealplan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.time.Year;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMealplanExistDto {
    private int year;
    private int month;
    private String SchoolName;
}
