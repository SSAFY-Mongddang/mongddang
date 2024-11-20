package com.onetwo.mongddang.domain.record.dto.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBloodSugarDto {

    Integer bloodSugarLevel;
}
