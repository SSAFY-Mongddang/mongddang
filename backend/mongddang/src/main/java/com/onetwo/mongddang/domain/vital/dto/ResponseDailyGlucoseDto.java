package com.onetwo.mongddang.domain.vital.dto;

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
public class ResponseDailyGlucoseDto {

    private Long id;

    private Integer bloodSugarLevel;

    private LocalDateTime measurementTime;

    private Null content;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean notification;

    public enum Status {
        low, normal, high
    }

}
