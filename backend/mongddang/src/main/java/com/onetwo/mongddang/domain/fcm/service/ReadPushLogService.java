package com.onetwo.mongddang.domain.fcm.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.errors.CustomFcmErrorCode;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.repository.PushLogRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadPushLogService {

    private final PushLogRepository pushLogRepository;
    private final UserRepository userRepository;

    public ResponseDto readPushLog(Long userId, Long notificationId) {

        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 알림 있는지 검증
        PushLog pushLog = pushLogRepository.findById(notificationId)
                .orElseThrow(()-> new RestApiException(CustomFcmErrorCode.NOTIFICATION_NOT_FOUND));

        // 이미 읽은 알림인지 검증
        if (!pushLog.getIsNew()) {
            throw new RestApiException(CustomFcmErrorCode.ALREADY_READ);
        }

        PushLog newPushLog = pushLog.toBuilder()
                .isNew(false)
                .build();
        pushLogRepository.save(newPushLog);

        //response
        ResponseDto response = ResponseDto.builder()
                .message("알림기록 조회에 성공했습니다.")
                .build();

        return response;
    }
}
