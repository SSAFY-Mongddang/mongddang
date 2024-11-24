package com.onetwo.mongddang.domain.mealplan.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.mealplan.dto.GetMealplanExistDto;
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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetMealplanIsExistService {

    private final MealplanRepository mealplanRepository;
    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;

    public ResponseDto getMealplanIsExist(Long userId, String nickname){

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

        // 없으면 data null로
        GetMealplanExistDto data = null;
        String message = "등록된 식단이 없습니다.";

        //가장 먼 미래의(?) 식단 하나 가져오기
        Optional<Mealplan> mealplanOptional = mealplanRepository.findFirstByChildOrderByStartTimeDesc(child);

        if (mealplanOptional.isPresent()) {
            Mealplan mealplan = mealplanOptional.get();
            data = GetMealplanExistDto.builder()
                    .year(mealplan.getStartTime().getYear())
                    .month(mealplan.getStartTime().getMonthValue())
                    .SchoolName(mealplan.getSchoolName())
                    .build();
            message = "가장 최신 식단 내역을 조회합니다.";
        }

        //식단 반환
        ResponseDto response = ResponseDto.builder()
                .message(message)
                .data(data)
                .build();

        return response;
    }
}
