package com.onetwo.mongddang.domain.game.mongddang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RequestMongddangListDto {

    private Long id;
    private String name;
    private String story;
    private int price;

    private Boolean isOwned;
    private Boolean isNew;
    private Boolean isMain;

}
