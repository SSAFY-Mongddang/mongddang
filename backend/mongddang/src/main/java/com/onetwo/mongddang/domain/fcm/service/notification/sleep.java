package com.onetwo.mongddang.domain.fcm.service.notification;

import com.onetwo.mongddang.domain.fcm.dto.Notification;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class sleep {

    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    @Scheduled(cron = "0 0 22 * * *") // 매일 22시에 실행
    public void sendSleepReminder() {
        // 안자고 있는 어린이 리스트
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.NOON); // 오늘 오후에 시작되는 수면 기록이 없어야 함
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        List<User> childrenList = userRepository.findChildUsersWithoutSleepingRecordToday(startOfDay, endOfDay);

        // 안자는 어린이들에게 알림 보내기
        for (User child : childrenList) {
            String message = "오후 10시입니다.\n몽땅친구들과 함께 잠자리에 들 준비를 하는 건 어떨까요?";
            Notification notification = Notification.builder()
                    .title("수면 시간 알림")
                    .message(message)
                    .receiver(child)
                    .child(child)
                    .medicationId("이상혈당")
                    .build();
            pushNotificationService.sendPushNotification(child, notification, PushLog.Category.game);
        }
    }
}
