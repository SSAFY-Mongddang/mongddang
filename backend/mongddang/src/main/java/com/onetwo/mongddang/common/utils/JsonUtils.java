package com.onetwo.mongddang.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.mongddang.domain.record.errors.CustomRecordErrorCode;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class JsonUtils {

    public void checkJsonTypeList(String contentJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode content = objectMapper.readTree(contentJson);

        // 리스트 형식 검증
        if (content.isArray()) {
            log.info("list type meal content: ");
        } else {
            throw new RestApiException(CustomRecordErrorCode.BAD_INGREDIENT_INPUT);
        }
    }

    public JsonNode JsonStringToJsonNode(String contentJson) {

        JsonNode content;

        // ObjectMapper 로 JSON 문자열을 JsonNode 로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            content = objectMapper.readTree(contentJson);
        } catch (
                IOException e) {
            // JSON 파싱 실패 시 예외 처리
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return content;
    }
}
