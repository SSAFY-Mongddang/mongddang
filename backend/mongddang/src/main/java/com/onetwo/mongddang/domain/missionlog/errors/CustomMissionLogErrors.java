package com.onetwo.mongddang.domain.missionlog.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomMissionLogErrors implements ErrorCode {

    MISSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MS000", "미션 목록 조회에 실패했습니다."),
    INVALID_MISSION_ID(HttpStatus.NOT_FOUND, "MS001", "잘못된 미션 ID 입니다."),
    CONDITION_NOT_MET(HttpStatus.BAD_REQUEST, "MS002", "조건이 충족되지 않은 미션입니다."),
    MISSION_REWARD_EXECUTION_FAILED(HttpStatus.BAD_REQUEST, "MS003", "미션 보상 수행에 실패했습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MS004", "이미 수행한 미션입니다."),
    MISSION_ALREADY_CREATED(HttpStatus.BAD_REQUEST, "MS005", "이미 오늘의 미션을 생성했습니다."),
    TODAY_MISSION_CREATED(HttpStatus.NOT_FOUND, "MS006", "자정이 지나 오늘의 미션을 새로 생성하였습니다. 미션 목록을 다시 불러와야 합니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
