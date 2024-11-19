package com.onetwo.mongddang.domain.medication.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onetwo.mongddang.domain.medication.dto.RequestRegisterMedicationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationUtils {

    public RequestRegisterMedicationDto jsonToMedicationDto(String requestRegisterMedicationDtoJson) {
        log.info("jsonToMedicationDto");

        // JSON 문자열을 RequestRegisterMedicationDto 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        RequestRegisterMedicationDto requestDto;
        try {
            requestDto = objectMapper.readValue(requestRegisterMedicationDtoJson, RequestRegisterMedicationDto.class);
        } catch (Exception e) {
            log.error("JSON 변환 오류: {}", e.getMessage());
            throw new RuntimeException("JSON 변환 오류");
        }

        return requestDto;
    }

}
