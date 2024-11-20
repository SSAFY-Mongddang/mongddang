package com.onetwo.mongddang.domain.fcm.service.push;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.dto.Notification;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SleepService {
    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    // 복약 시간 알림
    public String sendSleep(Long childId) {
        User child = userRepository.findById(childId).get();
        Notification notification = Notification.builder()
                .title("수면 시간 알림")
                .message("오후 10시입니다.\n몽땅친구들과 함께 잠자리에 들 준비를 하는 건 어떨까요?")
                .receiver(child)
                .child(child)
                .build();
        pushNotificationService.sendPushNotification(child, notification, PushLog.Category.game);

        return "어린이에게 수면 알림 발송 완료";
    }
}
