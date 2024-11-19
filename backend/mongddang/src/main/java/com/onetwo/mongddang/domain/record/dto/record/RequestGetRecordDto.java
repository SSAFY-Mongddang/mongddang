package com.onetwo.mongddang.domain.record.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestGetRecordDto {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "시작 날짜는 필수입니다.")
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$", message = "시작 날짜는 YYYY-MM 형식이어야 하며, 월은 01부터 12 사이여야 합니다.")
    private String startDate;

    @NotBlank(message = "종료 날짜는 필수입니다.")
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])$", message = "종료 날짜는 YYYY-MM 형식이어야 하며, 월은 01부터 12 사이여야 합니다.")
    private String endDate;
}
