package com.onetwo.mongddang.domain.chatWithMongddang.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.utils.GptUtils;
import com.onetwo.mongddang.domain.chatWithMongddang.prompt.PromptConstants;
import com.onetwo.mongddang.domain.game.mongddang.repository.MyMongddangRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkWithMongddangService {

    private final PromptConstants promptConstants;
    private final GptUtils gptUtils;
    private final MyMongddangRepository myMongddangRepository;
    private final ChatWithMongddangService chatWithMongddangService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ResponseDto talkWithMongddang(Long userId, byte[] audioBytes) throws JsonProcessingException {
        log.info("userId: {}", userId);

        // ====================== CLOVA Speech =======================
        // CLOVA Speech API URL
        String speechUrl = "https://clovaspeech-gw.ncloud.com/recog/v1/stt?lang=Kor";

        // 요청 헤더 설정
        HttpHeaders speechHeaders = new HttpHeaders();
        speechHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        speechHeaders.set("X-CLOVASPEECH-API-KEY", "a989a52d23e847efb02bf7ee1d41a8d9");

        // 요청 본문 설정
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(audioBytes, speechHeaders);
        RestTemplate restTemplate = new RestTemplate();

        // API 요청
        ResponseEntity<String> responseEntity = restTemplate.exchange(speechUrl, HttpMethod.POST, requestEntity, String.class);

        // API 응답 파싱
        String responseString = responseEntity.getBody();
        JsonNode rootNode = objectMapper.readTree(responseString);
//        log.info("API 응답: {}", rootNode);
        String message = rootNode.path("text").asText();
        log.info("인식된 메시지: {}", message);
        // ====================== CLOVA Speech =======================


        ResponseDto responseDto = chatWithMongddangService.startChatWithMongddang(userId, message);
        Map<String, Object> responseData = (Map<String, Object>) responseDto.getData(); // data를 Map으로 캐스팅
        String mongddangQuote = (String) responseData.get("message"); // message 추출
//        log.info("mongddang's answer 몽땅의 대답: {}", mongddangQuote);


        // ====================== CLOVA Voice =======================
        // CLOVA Voice API URL
        String voiceUrl = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";

        // 요청 헤더 설정
        log.info("set API headers for voice");
        HttpHeaders voiceHeaders = new HttpHeaders();

        // 필수 헤더 추가
        log.info("add required headers");
        voiceHeaders.set("x-ncp-apigw-api-key-id", "prp58mss1v");
        voiceHeaders.set("x-ncp-apigw-api-key", "d2l3IER8DNYGj3CRSsPi5uuwjEMkBPa9ucNdDCfl");
        voiceHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 데이터 설정
        log.info("set request data");
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("speaker", "dara_ang");
        requestData.add("text", mongddangQuote);

        // 요청 본문 설정
        log.info("set request entity");
        HttpEntity<MultiValueMap<String, String>> voiceRequestEntity = new HttpEntity<>(requestData, voiceHeaders);
        RestTemplate restTemplate2 = new RestTemplate();

        // API 요청
        log.info("send request to CLOVA Voice API");
        ResponseEntity<byte[]> responseEntity2 = restTemplate2.exchange(voiceUrl, HttpMethod.POST, voiceRequestEntity, byte[].class);

        // API 응답 파싱
        log.info("API response: {}", responseEntity2);
        if (responseEntity2.getStatusCode() == HttpStatus.OK) {
            log.info("API response is OK");

            // 응답 바디에서 음성 데이터 가져오기
            log.info("get audio data from response body");
            byte[] audioData = responseEntity2.getBody();

            // ResponseDto 반환
//            log.info("audioData: {}", audioData);
            return ResponseDto.builder()
                    .message("몽땅과의 새로운 대화를 시작합니다.")
                    .data(audioData) // 파일 경로를 반환
                    .build();
        } else {
            log.error("Failed to generate voice");
            // 오류 처리
            return ResponseDto.builder()
                    .message("음성 생성에 실패했습니다.")
                    .data(null)
                    .build();
        }
        // ====================== CLOVA Voice =======================
    }


}
