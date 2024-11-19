package com.onetwo.mongddang.domain.medication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RepeatTimesDto {

    @NotNull
    private LocalDateTime time;

    @NotNull
    private Long volume;

    private Long minGlucose;
    private Long maxGlucose;

}
