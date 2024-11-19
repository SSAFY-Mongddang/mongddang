package com.onetwo.mongddang.domain.game.title.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomTitleErrorCode implements ErrorCode {
    NOT_FOUND_TITLE_LIST(HttpStatus.BAD_REQUEST, "T000", "칭호 목록 조회에 실패했습니다."),
    INVALID_TITLE_ID(HttpStatus.NOT_FOUND, "T001", "잘못된 칭호 ID 입니다."),
    TITLE_NOT_OWNED(HttpStatus.BAD_REQUEST, "T002", "보유하지 않은 칭호입니다."),
    TITLE_ALREADY_OWNED(HttpStatus.BAD_REQUEST, "T003", "이미 소유한 칭호입니다."),
    NEW_LABEL_REMOVAL(HttpStatus.BAD_REQUEST, "T004", "새로운 칭호 표시 제거에 실패했습니다."),
    MAIN_SET_EXCEPTION(HttpStatus.BAD_REQUEST, "T005", "메인 칭호 설정에 실패했습니다."),
    ALREADY_TITLE_SET_MAIN(HttpStatus.BAD_REQUEST, "T006", "이미 메인 칭호로 설정되어 있습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
