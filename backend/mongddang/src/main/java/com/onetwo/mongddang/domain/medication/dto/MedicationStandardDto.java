package com.onetwo.mongddang.domain.medication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationStandardDto {

    private Long minGlucose;
    private Long maxGlucose;

    @NotNull(message = "복용량을 입력해주세요.")
    private Long volume;

}
