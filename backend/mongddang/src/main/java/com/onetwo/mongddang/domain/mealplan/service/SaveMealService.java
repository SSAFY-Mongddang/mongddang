package com.onetwo.mongddang.domain.mealplan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestSaveMealplanDto;
import com.onetwo.mongddang.domain.mealplan.model.Mealplan;
import com.onetwo.mongddang.domain.mealplan.repository.MealplanRepository;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.error.CustomCtoPErrorCode;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class  SaveMealService {

    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;
    private final RecordRepository recordRepository;
    private final MealplanRepository mealplanRepository;

    public ResponseDto saveMealplan(RequestSaveMealplanDto requestSaveMealplanDto, Long userId) {

        // user 있는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 닉네임으로 어린이 찾기
        User child = userRepository.findByNickname(requestSaveMealplanDto.getNickname())
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        // user가 보호자라면 닉네임 어린이와 연결되어있는지 확인
        if (user.getRole() == User.Role.protector) {
            log.info("this user is protector");

            //연결 여부 검사
            if (ctoPRepository.findByChildAndProtector(user, child).isEmpty()) {
                throw new RestApiException(CustomCtoPErrorCode.CHILD_NOT_LINKED);
            }
        }

        List<Mealplan> mealplans = new ArrayList<>();

        for (int i = 0; i < requestSaveMealplanDto.getMealList().size(); i++) {
            JsonNode meal = requestSaveMealplanDto.getMealList().get(i);

            // 식사 날짜 입력 (json -> string -> LocalDateTime)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = meal.get("date").asText();
            LocalDate dateDate = LocalDate.parse(dateString, formatter);
            LocalDateTime date = dateDate.atStartOfDay();

            // 조중석식 여부
            Mealplan.MealTimeType mealTimeType = null;
            switch (meal.get("mealTime").asText()) {
                case "조식":
                    mealTimeType = Mealplan.MealTimeType.breakfast;
                    break;
                case "중식":
                    mealTimeType = Mealplan.MealTimeType.lunch;
                    break;
                case "석식":
                    mealTimeType = Mealplan.MealTimeType.dinner;
                    break;
            }

            Mealplan mealplan = Mealplan.builder()
                    .category(Mealplan.RecordCategoryType.meal)
                    .child(child)
                    .content(meal.get("meal"))
                    .imageUrl(null)
                    .startTime(date)
                    .endTime(null)
                    .isDone(false)
                    .mealTime(mealTimeType)
                    .schoolName(meal.get("school").asText())
                    .build();

            mealplans.add(mealplan);
        }
        mealplanRepository.saveAll(mealplans);

        // 반환
        ResponseDto response = ResponseDto.builder()
                .message("식단 저장을 성공했습니다.")
                .build();

        return response;
    }
}
