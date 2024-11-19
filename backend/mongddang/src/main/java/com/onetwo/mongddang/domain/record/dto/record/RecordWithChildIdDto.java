package com.onetwo.mongddang.domain.record.dto.record;

import com.fasterxml.jackson.databind.JsonNode;
import com.onetwo.mongddang.domain.record.converter.JsonConverter;
import com.onetwo.mongddang.domain.record.model.Record;
import jakarta.persistence.*;
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
public class RecordWithChildIdDto {

    @NotNull
    private Long id;

    @NotNull
    private Long childId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Record.RecordCategoryType category;

    @NotNull
    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Lob
    @Convert(converter = JsonConverter.class)
    private JsonNode content;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "meal_time")
    @Enumerated(EnumType.STRING)
    private Record.MealTimeType mealTime;

}
