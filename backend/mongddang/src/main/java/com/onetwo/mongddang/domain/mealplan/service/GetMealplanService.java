package com.onetwo.mongddang.domain.mealplan.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.RequestMealInfoDto;
import com.onetwo.mongddang.domain.mealplan.error.CustomMealplanErrorCode;
import com.onetwo.mongddang.domain.mealplan.model.Mealplan;
import com.onetwo.mongddang.domain.mealplan.repository.MealplanRepository;
import com.onetwo.mongddang.domain.user.error.CustomCtoPErrorCode;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetMealplanService {

    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;
    private final MealplanRepository mealplanRepository;

    public ResponseDto getTodayMealplan(Long userId, String mealTime, String nickname){

        // user 있는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 닉네임으로 어린이 찾기
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        log.info("ChildId : {}", child.getId());

        // user가 보호자라면 닉네임 어린이와 연결되어있는지 확인
        if (user.getRole() == User.Role.protector) {
            log.info("this user is protector");

            //연결 여부 검사
            if (ctoPRepository.findByChildAndProtector(user, child).isEmpty()) {
                throw new RestApiException(CustomCtoPErrorCode.CHILD_NOT_LINKED);
            }
        }

        // enum 안에 있는지 검사
        isValidMealTimeType(mealTime);

        // child,날짜,mealTime에 맞게 조회
        Mealplan todayMeal = mealplanRepository.findOneByChildIdAndMealTimeToday(child.getId(),mealTime)
                .orElseThrow(()-> new RestApiException(CustomMealplanErrorCode.MEAL_INFO_NOT_FOUND));

        //식단 반환
        ResponseDto response = ResponseDto.builder()
                .message("오늘의 식단 조회를 성공했습니다.")
                .data(todayMeal.getContent())
                .build();

        return response;
    }

    // mealTime이 enum중에 하나인지 체크
    public void isValidMealTimeType(String mealTimeString) {
        if (mealTimeString == null || mealTimeString.isEmpty()) {
            throw new RestApiException(CustomMealplanErrorCode.MEAL_TIME_NOT_IN_ENUM);
        }

        // Enum의 모든 값과 비교하여 유효성 체크
        for (Mealplan.MealTimeType type : Mealplan.MealTimeType.values()) {
            if (type.name().equalsIgnoreCase(mealTimeString)) {
                return;
            }
        }

        throw new RestApiException(CustomMealplanErrorCode.MEAL_TIME_NOT_IN_ENUM);
    }
}
