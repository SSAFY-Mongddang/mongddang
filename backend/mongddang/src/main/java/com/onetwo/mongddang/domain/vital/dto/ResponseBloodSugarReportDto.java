package com.onetwo.mongddang.domain.vital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBloodSugarReportDto {

    private List<GlucoseMeasurementTimeDto> glucoseMeasurementItmeList;
    private float gmi; // 혈당 관리 지표
    private float abg; // 평균 혈당
    private float cv; // 혈당 변동성
    private float tir; // 목표 범위 내 비율

}
