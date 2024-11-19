package com.onetwo.mongddang.domain.record.dto.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecordDetailsDto {
    private List<RecordWithChildIdDto> meal;
    private List<RecordWithChildIdDto> exercise;
    private List<RecordWithChildIdDto> sleep;
    private List<RecordWithChildIdDto> medication;
}
