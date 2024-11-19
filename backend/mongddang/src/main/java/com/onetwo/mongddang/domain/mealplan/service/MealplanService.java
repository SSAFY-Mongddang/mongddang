package com.onetwo.mongddang.domain.mealplan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.MealplanDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestMealInfoDto;
import com.onetwo.mongddang.domain.mealplan.error.CustomMealplanErrorCode;
import com.onetwo.mongddang.domain.user.error.CustomCtoPErrorCode;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealplanService {

    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;

    @Value("${school.key}")
    private String schoolKey;

    @Value("${school.url}")
    private String schoolUrl;

    @Value("${meal.key}")
    private String mealKey;

    @Value("${meal.url}")
    private String mealUrl;

    public ResponseDto getMealplan(RequestMealInfoDto requestMealInfoDto, Long userId) throws JsonProcessingException {
        // user 있는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 닉네임으로 어린이 찾기
        User child = userRepository.findByNickname(requestMealInfoDto.getNickname())
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        // user가 보호자라면 닉네임 어린이와 연결되어있는지 확인
        if (user.getRole() == User.Role.protector) {
            log.info("this user is protector");

            //연결 여부 검사
            if (ctoPRepository.findByChildAndProtector(user, child).isEmpty()) {
                throw new RestApiException(CustomCtoPErrorCode.CHILD_NOT_LINKED);
            }
        }

        // 학교 이름으로 학교 코드 / 교육청 코드 뽑기 (공공 api 사용)
        JsonNode schoolInfo = getSchoolInfo(requestMealInfoDto.getSchoolName());
        log.info("학교 코드 추출 완료!!");
        log.info(schoolInfo.toString());

        String schoolCode = schoolInfo.get(0).get("SD_SCHUL_CODE").asText(); // 학교 코드
        String officeCode = schoolInfo.get(0).get("ATPT_OFCDC_SC_CODE").asText(); // 교육청 코드

        // 연월 정보로 시작 시간, 종료 시간 yyyymmdd 모양으로 뽑기
        YearMonth month = requestMealInfoDto.getMonth();

        // 2024-11-01을 20241101 형식으로 바꾸는 포매터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String startDay = month.atDay(1).format(formatter);
        String endDay = month.atEndOfMonth().format(formatter);

        // 조증석식 코드로 변환(조식 : 1 / 중식 : 2 / 석식 : 3)
        String mealTimeCode = null;
        switch (requestMealInfoDto.getMealTime()) {
            case RequestMealInfoDto.MealType.breakfast:
                mealTimeCode = "1";
                break;
            case RequestMealInfoDto.MealType.lunch:
                mealTimeCode = "2";
                break;
            case RequestMealInfoDto.MealType.dinner:
                mealTimeCode = "3";
                break;
        }

        // 학교 코드, 교육청 코드, 식사시간, 시작시간, 종료 시간 넣어서 급식 식단 뽑기 (공공 api사용)
        JsonNode mealInfoList = getMeal(schoolCode, officeCode, mealTimeCode, startDay, endDay);
        log.info("mealInfo : {}", mealInfoList.get(0).toString());

        List<MealplanDto> data = new ArrayList<>();

        for (int i = 0; i < mealInfoList.size(); i++) {
            // 나온 정보 for문 돌리면서 적절히 묶기. mealplanDto 사용
            JsonNode mealInfo = mealInfoList.get(i);

            // 날짜 타입 문자열을 LocalDate로 변환
            String dateString = mealInfo.get("MLSV_YMD").asText();
            LocalDate date = LocalDate.parse(dateString, formatter);

            // 식단 문자열을 List로 변환 + 알러지 정보 떼기
            String[] mealString = mealInfo.get("DDISH_NM").asText().split("<br/>");

            // 괄호 안의 내용을 제거하고 공백을 제거
            List<String> mealList = Arrays.stream(mealString)
                    .map(item -> item.replaceAll("\\s*\\(.*?\\)", "").trim())
                    .toList();

            MealplanDto oneMeal = MealplanDto.builder()
                    .school(mealInfo.get("SCHUL_NM").asText())
                    .date(date)
                    .mealTime(mealInfo.get("MMEAL_SC_NM").asText())
                    .meal(mealList)
                    .build();

            data.add(oneMeal);
        }
        // 에러 처리도 좀 해라

        //식단 반환
        ResponseDto response = ResponseDto.builder()
                .message("식단 조회를 성공했습니다.")
                .data(data)
                .build();

        return response;
    }

    // 학교,교육청 api 코드 받아오기
    public JsonNode getSchoolInfo(String schoolname) {
        try {
            log.info("start getSchoolInfo");
            String apiKey = schoolKey; // 발급받은 API Key를 여기에 입력하세요.
            String apiUrl = schoolUrl + "?KEY=" + apiKey + "&Type=json&SCHUL_NM=" + schoolname; // 학교명으로 검색

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            // JSON 데이터를 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // "schoolInfo"의 "row" 데이터만 반환
            if (rootNode.has("schoolInfo") && rootNode.get("schoolInfo").size() > 1) {
                JsonNode rowNode = rootNode.get("schoolInfo").get(1).path("row");
                return rowNode;
            } else {
                throw new RestApiException(CustomMealplanErrorCode.SCHOOL_INFO_RETR_FAIL);
            }
        } catch (Exception e) {
            throw new RestApiException(CustomMealplanErrorCode.SCHOOL_INFO_RETR_FAIL);
        }
    }

    // 급식표 가져오기
    public JsonNode getMeal(String schoolCode, String officeCode,
                            String mealTimeCode, String startDay,
                            String endDay) {
        try {
            String apiKey = mealKey; // 발급받은 API Key를 여기에 입력하세요.
            String apiUrl = mealUrl + "?KEY=" + apiKey + "&Type=json&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=" + officeCode
                    + "&SD_SCHUL_CODE=" + schoolCode + "&MLSV_FROM_YMD=" + startDay
                    + "&MLSV_TO_YMD=" + endDay + "&MMEAL_SC_CODE=" + mealTimeCode;

            log.info("meal api Url : {}", apiUrl);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            // JSON 데이터를 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // "schoolInfo"의 "row" 데이터만 반환
            if (rootNode.has("mealServiceDietInfo") && rootNode.get("mealServiceDietInfo").size() > 1) {
                JsonNode rowNode = rootNode.get("mealServiceDietInfo").get(1).path("row");
                return rowNode;
            } else {
                throw new RestApiException(CustomMealplanErrorCode.MEAL_INFO_RETR_FAIL);
            }
        } catch (Exception e) {
            throw new RestApiException(CustomMealplanErrorCode.MEAL_INFO_RETR_FAIL);
        }
    }
}
