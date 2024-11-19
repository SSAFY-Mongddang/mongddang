package com.onetwo.mongddang.domain.record.dto.record;

import com.onetwo.mongddang.domain.record.model.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OngoingResponseDto {

    private Record.RecordCategoryType category;
    private LocalDateTime startTime;

}
