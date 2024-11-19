package com.onetwo.mongddang.common.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

// 모두가 공통으로 쓰는 가장 바깥 포장지입니다.
// null 값인 항목은 응답에 포함되지 않습니다.
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    @Builder.Default
    private int code = 200;

    private String message;

    private Object data;
}
