package com.onetwo.mongddang.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

// 칭호, 몽땅 정보를 담는 dto입니다.
@Getter
@Builder
public class MaingameDto {
    private Long id;
    private String name;
}
