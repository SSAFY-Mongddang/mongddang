package com.onetwo.mongddang.domain.fcm.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.dto.PushLogDto;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.repository.PushLogRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetPushLogService {
    private final PushLogRepository pushLogRepository;
    private final UserRepository userRepository;

    public ResponseDto GetPushLog(Long userId) {

        //사용자 유무 검사
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        // 알림 로그 데이터 조회
        List<PushLog> pushLogs = pushLogRepository.findAllByUserId(userId);

        // 알림 기록이 없으면 null반환
        if (pushLogs.isEmpty()) {
            return ResponseDto.builder()
                    .message("알림 기록이 없습니다.")
                    .data(null)
                    .build();
        }

        // 반환 리스트 초기화
        List<PushLogDto> pushLogDtoList = new ArrayList<>();
        // 하나씩 dto에 맞춰 감싸기
        for (PushLog pushLog : pushLogs) {
            PushLogDto pushLogDto = PushLogDto.builder()
                    .notificationId(pushLog.getId())
                    .category(pushLog.getCategory())
                    .content(pushLog.getContent())
                    .createdAt(pushLog.getCreatedAt())
                    .isNew(pushLog.getIsNew())
                    .build();
            // list에 넣기
            pushLogDtoList.add(pushLogDto);
        }

        //response
        ResponseDto response = ResponseDto.builder()
                .message("알림기록 조회에 성공했습니다.")
                .data(pushLogDtoList)
                .build();

        return response;

    }
}
