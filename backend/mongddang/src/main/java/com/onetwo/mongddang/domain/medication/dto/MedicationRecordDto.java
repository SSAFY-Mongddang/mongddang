package com.onetwo.mongddang.domain.medication.dto;

import com.onetwo.mongddang.domain.record.model.Record;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationRecordDto {

    private Long id;

    private Long childId;

    @NotNull
    private String name;

    private String imageUrl;

    @NotNull
    private Long volume;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RouteType route;

    @NotNull
    private boolean isRepeated;

    @NotNull
    private String repeatDays;

    @NotNull
    private LocalDateTime repeatStartTime;

    @NotNull
    private LocalDateTime repeatEndTime;

    @NotNull
    private Boolean isDone;

    @NotNull
    private Record.RecordCategoryType category;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long min_glucose;
    private Long max_glucose;

    public enum RouteType {
        mouth, injection
    }
}
