package com.onetwo.mongddang.domain.medication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDeleteMedicationDto {

    @NotNull(message = "닉네임을 입력해주세요.")
    @Valid
    private String nickname;

    @NotNull(message = "약품 관리 아이디를 입력해주세요.")
    @Valid
    private Long medicationManagementId;

}
