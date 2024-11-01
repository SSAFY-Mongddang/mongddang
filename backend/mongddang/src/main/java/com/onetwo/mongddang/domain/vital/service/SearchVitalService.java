package com.onetwo.mongddang.domain.vital.service;

import com.onetwo.mongddang.common.ResponseDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.user.service.CheckUserService;
import com.onetwo.mongddang.domain.vital.dto.ResponseCurrentGlucoseDto;
import com.onetwo.mongddang.domain.vital.dto.ResponseSolidGlucoseDto;
import com.onetwo.mongddang.domain.vital.error.CustomVitalErrorCode;
import com.onetwo.mongddang.domain.vital.model.Vital;
import com.onetwo.mongddang.domain.vital.repository.VitalRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchVitalService {
    private final VitalRepository vitalRepository;
    private final UserRepository userRepository;
    private final CheckUserService checkUserService;

    @Transactional
    public ResponseDto SearchCurrentGlucose(Long childId){
        log.info("SearchCurrentGlucose childId: {}", childId);
        User user = checkUserService.isEqualRole(childId, "child");
        Vital currentVital = vitalRepository.findTopByChildIdOrderByIdDesc(user.getId())
                .orElseThrow(() -> new RestApiException(CustomVitalErrorCode.THIS_USER_VITAL_LOG_NOT_FOUND));
        ResponseCurrentGlucoseDto currentGlucoseDto = ResponseCurrentGlucoseDto.builder()
                .id(currentVital.getId())
                .bloodSugarLevel(currentVital.getBloodSugarLevel())
                .measurementTime(currentVital.getMeasurementTime())
                .status(currentVital.getStatus())
                .build();
        Map<String,Object> result = new HashMap<>();
        result.put("bloodSugar", currentGlucoseDto);
        ResponseDto responseDto = ResponseDto.builder()
                .code(200)
                .message(" 최신 혈당기록 조회에 성공하였습니다.")
                .data(result)
                .build();
        return responseDto;
    }
    @Transactional
    public ResponseDto SearchThisDayVital(Long childId, String date){
        log.info("SearchThisDayVital childId: {}, date: {}", childId, date);
        User user = checkUserService.isEqualRole(childId, "child");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Vital> glucoseList = vitalRepository.findAllByDateAndChildId(localDate, user.getId());
        List<ResponseSolidGlucoseDto> solidGlucoseDtoList = new ArrayList<>();

        solidGlucoseDtoList = glucoseList.stream()
                .map(glucose -> ResponseSolidGlucoseDto.builder()
                        .id(glucose.getId())
                        .bloodSugarLevel(glucose.getBloodSugarLevel())
                        .content(glucose.getContent())
                        .status(glucose.getStatus())
                        .measurementTime(glucose.getMeasurementTime())
                        .isNotification(glucose.getIsNotification())
                        .build())
                .collect(Collectors.toList());
        Map<String,Object> result = new HashMap<>();
        result.put("bloodSugar", solidGlucoseDtoList);
        String inform = String.format("%s일 혈당 조회에 성공했습니다.", localDate);
        // ResponseDateGlucoseDto 객체를 빌더를 통해 생성
        ResponseDto responseDto = ResponseDto.builder()
                .code(200)
                .message(inform)
                .data(result)
                .build();
        return responseDto;
    }

}
