package com.onetwo.mongddang.domain.game.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestClaimAchievementDto {
    private Long achievementId;

}
