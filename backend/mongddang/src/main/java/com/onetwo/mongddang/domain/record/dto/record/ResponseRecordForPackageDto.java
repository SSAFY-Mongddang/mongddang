package com.onetwo.mongddang.domain.record.dto.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseRecordForPackageDto {
    private String day; // 날짜
    private RecordDetailsDto records; // 각 기록들
}
