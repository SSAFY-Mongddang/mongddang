package com.onetwo.mongddang.domain.vital.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.onetwo.mongddang.domain.vital.model.BloodGlucose;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodGlucoseRequest {

    private Integer bloodSugarLevel;

    private LocalDateTime measurementTime;

    private String packageName;

}
