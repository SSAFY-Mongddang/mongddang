package com.onetwo.mongddang.errors.handler;

import com.onetwo.mongddang.errors.errorcode.CommonErrorCode;
import com.onetwo.mongddang.errors.errorcode.ErrorCode;
import com.onetwo.mongddang.errors.exception.RestApiException;
import com.onetwo.mongddang.errors.response.ErrorResponse;
import com.onetwo.mongddang.errors.response.ErrorResponse.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(RestApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    // MethodArgumentNotValidException 처리 (유효성 검사 실패)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toList());

        ErrorResponse response = ErrorResponse.builder()
                .code(CommonErrorCode.INVALID_PARAMETER.getCode())  // U003 코드 포함
                .message(CommonErrorCode.INVALID_PARAMETER.getMessage())
                .errors(validationErrors)
                .build();

        return ResponseEntity.status(CommonErrorCode.INVALID_PARAMETER.getHttpStatus()).body(response);
    }
}
