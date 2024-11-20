package com.onetwo.mongddang.domain.record.dto.medication;

import com.onetwo.mongddang.domain.medication.dto.MedicationStandardDto;
import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.record.converter.JsonConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Lob;
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
public class MedicationDayInfo {

    private String name;
    private String time;
    private Long volume;
    private MedicationManagement.RouteType route;
    private Boolean isRepeat;
    private List<String> repeatDays;

    @Lob
    @Convert(converter = JsonConverter.class)
    private LocalDateTime repeatStartTime;

    @Lob
    @Convert(converter = JsonConverter.class)
    private LocalDateTime repeatEndTime;
    
    private Boolean isFast;
    private List<String> repeatTimes;
    private List<MedicationStandardDto> standards;

}
