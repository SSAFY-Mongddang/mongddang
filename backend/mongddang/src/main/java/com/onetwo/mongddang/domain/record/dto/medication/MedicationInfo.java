package com.onetwo.mongddang.domain.record.dto.medication;

import com.onetwo.mongddang.domain.medication.dto.MedicationStandardDto;
import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
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
public class MedicationInfo {

    private String name;
    private String imageUrl;
    private MedicationManagement.RouteType route;
    private boolean isRepeated;
    private List<String> repeatDays;
    private LocalDateTime repeatStartTime;
    private LocalDateTime repeatEndTime;
    private boolean isFast;
    private List<String> repeatTimes;
    private List<MedicationStandardDto> standards;

}
