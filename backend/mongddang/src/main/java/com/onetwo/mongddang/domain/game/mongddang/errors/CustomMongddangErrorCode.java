package com.onetwo.mongddang.domain.game.mongddang.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomMongddangErrorCode implements ErrorCode {
    NOT_FOUND_COLLECTION(HttpStatus.BAD_REQUEST, "CL000", "몽땅 목록 조회에 실패했습니다."),
    INVALID_COLLECTION_ID(HttpStatus.NOT_FOUND, "CL001", "존재하지 않은 몽땅입니다."),
    NEW_LABEL_REMOVAL(HttpStatus.BAD_REQUEST, "CL002", "새로운 몽땅 표시 제거에 실패했습니다."),
    LABEL_ALREADY_REMOVED(HttpStatus.BAD_REQUEST, "CL003", "이미 새로운 몽땅 표시가 제거되었습니다."),
    INSUFFICIENT_COIN(HttpStatus.BAD_REQUEST, "CL004", "코인이 부족합니다."),
    CHARACTER_NOT_OWNED(HttpStatus.BAD_REQUEST, "CL005", "보유하지 않은 몽땅입니다."),
    CHARACTER_ALREADY_OWNED(HttpStatus.BAD_REQUEST, "CL006", "이미 모집된 몽땅입니다."),
    MAIN_SET_EXCEPTION(HttpStatus.BAD_REQUEST, "CL007", "메인 몽땅 설정에 실패했습니다."),
    RECRUITMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "CL008", "몽땅 모집에 실패했습니다."),
    ALREADY_SET_MAIN(HttpStatus.BAD_REQUEST, "CL009", "이미 메인 몽땅으로 설정되어 있습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
