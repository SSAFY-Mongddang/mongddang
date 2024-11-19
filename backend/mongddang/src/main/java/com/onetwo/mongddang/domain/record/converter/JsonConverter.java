package com.onetwo.mongddang.domain.record.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConverter implements AttributeConverter<JsonNode,String> {
    // JSON 변환을 위한 Jackson 라이브러리 사용
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        // JSON 객체 -> 문자열 변환
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("JSON 직렬화 실패", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        // 문자열 -> JSON 객체 변환
        try {
            return dbData == null ? null : objectMapper.readTree(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("JSON 역직렬화 실패", e);
        }
    }
}
