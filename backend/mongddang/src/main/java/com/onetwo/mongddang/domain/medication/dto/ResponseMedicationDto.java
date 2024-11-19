package com.onetwo.mongddang.domain.medication.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseMedicationDto {

    private List<RegisteredMedicationDto> medications;

}
