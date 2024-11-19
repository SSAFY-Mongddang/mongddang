package com.onetwo.mongddang.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectedUserInfoDto {
    private String name;
    private String nickname;
}
