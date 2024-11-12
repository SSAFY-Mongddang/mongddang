package com.onetwo.mongddang.domain.vital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlucoseMeasurementTimeDto {

    private LocalDateTime measurementTime;
    private int bloodSugarLevel;


}
