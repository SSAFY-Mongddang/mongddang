package com.onetwo.mongddang.domain.game.achievement.dto;

import com.onetwo.mongddang.domain.game.achievement.model.Achievement.AchievementCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseAchievementListDto {

    private Long titleId;
    private String titleName;
    private String description;
    private int executionCount;
    private int count;
    private AchievementCategory category;

    private Boolean isOwned;
    private Boolean isNew;
    private Boolean isMain;


}
