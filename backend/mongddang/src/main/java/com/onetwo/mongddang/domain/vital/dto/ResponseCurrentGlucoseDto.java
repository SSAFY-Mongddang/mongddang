package com.onetwo.mongddang.domain.vital.dto;

import com.onetwo.mongddang.domain.vital.model.Vital;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCurrentGlucoseDto {
    @NotNull(message = "최신 혈당 id 필드는 null일 수 없습니다.")
    private long id;
    @NotNull(message = "최신 혈당 수치는 null일 수 없습니다.")
    private int bloodSugarLevel;
    @NotNull(message = "최신 혈당 측정시간은 null 일 수 없습니다.")
    private LocalDateTime measurementTime;
    @NotNull(message = "최신 혈당 status는 null 일 수 없습니다.")
    @Enumerated(EnumType.STRING)
    private Vital.GlucoseStatusType status;
}
