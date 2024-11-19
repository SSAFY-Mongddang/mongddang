package com.onetwo.mongddang.common.s3.errors;

import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomS3ErrorCode implements ErrorCode {
    IMAGE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "S000", "S3에 이미지 파일 업로드 실패"),
    EMPTY_FILE_EXCEPTION(HttpStatus.NOT_FOUND, "S001", "파일이 비어있습니다."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.BAD_REQUEST, "S002", "이미지 업로드 중 IO 예외 발생"),
    NO_FILE_EXTENTION(HttpStatus.BAD_REQUEST, "S003", "파일 확장자가 없습니다."),
    INVALID_FILE_EXTENTION(HttpStatus.NOT_FOUND, "S004", "유효하지 않은 파일 확장자입니다."),
    PUT_OBJECT_EXCEPTION(HttpStatus.NOT_FOUND, "S005", "오브젝트 업로드 중 예외 발생"),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.NOT_FOUND, "S006", "이미지 삭제 중 IO 예외 발생"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
