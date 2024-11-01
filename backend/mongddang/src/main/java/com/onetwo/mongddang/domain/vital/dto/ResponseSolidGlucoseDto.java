package com.onetwo.mongddang.domain.vital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ResponseSolidGlucoseDto {
    @NotNull
    private Long id;

    @NotNull
    private Integer bloodSugarLevel;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime measurementTime;

    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vital.GlucoseStatusType status;

    @NotNull
    private Boolean isNotification;

}
