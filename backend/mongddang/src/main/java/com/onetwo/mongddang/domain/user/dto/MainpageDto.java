package com.onetwo.mongddang.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainpageDto {

    String nickname;
    Long mainMongddangId;
    String mainTitleName;
    int coin;
    Boolean UnreadNotification; // 읽지 않은 알림 있는지
    Boolean UnclaimedMissionReward; // 수행했지만 수령하지 않은 알림 보상있는지
    Boolean UnclaimedAchivementReward; // 완료했지만 수령하지 않은 업적 보상 있는지

}
