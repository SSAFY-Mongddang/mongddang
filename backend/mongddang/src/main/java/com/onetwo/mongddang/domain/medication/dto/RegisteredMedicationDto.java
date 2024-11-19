package com.onetwo.mongddang.domain.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredMedicationDto {

    private Long id;
    private String name;
    private String imageUrl;
    private LocalDateTime repeatStartTime;
    private LocalDateTime repeatEndTime;
    private Boolean isFast;
    private List<String> repeatTimes;
    private List<MedicationStandardDto> standards;

}
