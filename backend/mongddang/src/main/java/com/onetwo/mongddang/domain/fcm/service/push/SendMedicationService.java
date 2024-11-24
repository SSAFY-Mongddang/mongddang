package com.onetwo.mongddang.domain.fcm.service.push;

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
public class SendMedicationService {

    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    // 복약 시간 알림
    public String sendMedication(Long childId) {
        User child = userRepository.findById(childId).get();
        Notification notification = Notification.builder()
                .title("복약 알림")
                .message("인슐린을 맞을 시간이에요.")
                .receiver(child)
                .child(child)
                .medicationId("에시 복약 알림")
                .build();
        pushNotificationService.sendPushNotification(child, notification, PushLog.Category.medication);

        return "어린이에게 복약 시간 알림 발송 완료";
    }
}
