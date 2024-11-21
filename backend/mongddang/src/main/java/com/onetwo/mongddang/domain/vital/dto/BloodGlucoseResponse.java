package com.onetwo.mongddang.domain.vital.dto;

import com.onetwo.mongddang.domain.vital.model.BloodGlucose;
import com.onetwo.mongddang.domain.vital.model.Vital;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodGlucoseResponse {
    private Long id;

    private Integer bloodSugarLevel;

    private LocalDateTime measurementTime;

    private Null content;

    @Enumerated(EnumType.STRING)
    private  Vital.GlucoseStatusType status;

    private Boolean notification;

    private String packageName;
}
