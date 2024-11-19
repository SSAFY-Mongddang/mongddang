package com.onetwo.mongddang.domain.medication.dto;

import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegisterMedicationDto {

    @NotNull(message = "유저 닉네임은 필수입니다.")
    private String nickname;

    @NotNull(message = "약품 이름은 필수입니다.")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "복약 경로는 필수입니다.")
    @Valid
    private MedicationManagement.RouteType route;

    @NotNull(message = "복약 여부는 필수입니다.")
    private Boolean isRepeated;

    @NotNull(message = "복약 요일은 필수입니다.")
    private List<String> repeatDays;

    @NotNull(message = "복약 시작 시간은 필수입니다.")
    private LocalDateTime repeatStartTime;

    @NotNull(message = "복약 종료 시간은 필수입니다.")
    private LocalDateTime repeatEndTime;

    @NotNull(message = "복약 시간은 필수입니다.")
    private Boolean isFast;

    @NotNull(message = "복약 시간은 필수입니다.")
    private List<String> repeatTimes;

    private List<MedicationStandardDto> standards;

}
