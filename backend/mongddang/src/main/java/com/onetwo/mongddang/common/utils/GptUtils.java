package com.onetwo.mongddang.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.url}")
    private String url;

    // 온도를 기본값으로 사용한 메서드
    public String requestGpt(String message) throws JsonProcessingException {
        return requestGpt(message, 0.7f); // 기본 온도 0.7
    }

    // 온도를 명시적으로 설정할 수 있는 메서드
    public String requestGpt(String message, float temperature) throws JsonProcessingException {
        // API 요청 바디 생성
        String requestBody = this.createRequestBody(message, temperature);
        log.info("Request Body: {}", requestBody);

        // API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // API 요청
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        log.info("Response: {}", response);

        // API 응답 파싱
        JsonNode rootNode = objectMapper.readTree(response);
        log.info("rootNode : {}", rootNode.get("choices").get(0).get("message").get("content").asText());

        // API 응답 중에서 답변 부분 추출하여 반환
        return rootNode.path("choices").get(0).path("message").path("content").asText();
    }

    // 기본 온도를 사용한 메서드
    public String createRequestBody(String message) throws JsonProcessingException {
        return createRequestBody(message, 0.7f); // 기본값 0.7f를 사용
    }

    // 온도를 명시적으로 설정할 수 있는 메서드
    public String createRequestBody(String message, float temperature) throws JsonProcessingException {
        ObjectNode requestBody = objectMapper.createObjectNode();

        // 모델 설정
        requestBody.put("model", "gpt-4o");

        // 대화 내용 설정
        ArrayNode messages = requestBody.putArray("messages");
        ObjectNode messageNode = objectMapper.createObjectNode();
        messageNode.put("role", "system");
        messageNode.put("content", message);
        messages.add(messageNode);

        // 온도 설정
        requestBody.put("temperature", temperature);

        return objectMapper.writeValueAsString(requestBody);
    }

}
