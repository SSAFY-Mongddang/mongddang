package com.onetwo.mongddang.domain.medication.dto;

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
public class RequestMedicationDto {

    @NotNull(message = "nickname은 필수값입니다.")
    private String nickname;

    @NotNull(message = "name은 필수값입니다.")
    private String name;

    @NotNull(message = "repeatStartTime은 필수값입니다.")
    private LocalDateTime repeatStartTime;
//    sdcasdasasdsdasdasd

    @NotNull(message = "repeatEndTime은 필수값입니다.")
    private LocalDateTime repeatEndTime;

    @NotNull(message = "isFast은 필수값입니다.")
    private Boolean isFast;

    @NotNull(message = "repeatTimes은 필수값입니다.")
    private List<String> repeatTimes;

    @NotNull(message = "standards은 필수값입니다.")
    private List<MedicationStandardDto> standards;


    private String image;

}
