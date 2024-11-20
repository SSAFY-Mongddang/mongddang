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
public class GlycemiaProtectorService {

    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    // 저혈당
    public String sendLowProtector(Long protectorId, Long childId) {
        User protector = userRepository.findById(protectorId).get();
        User child = userRepository.findById(childId).get();
        Notification notification = Notification.builder()
                .title("이상 혈당 지속 알림")
                .message("'" + child.getNickname() + "'" + "의 저혈당 증상 15분 이상 지속 중.")
                .receiver(protector)
                .child(child)
                .build();
        pushNotificationService.sendPushNotification(protector, notification, PushLog.Category.blood_sugar);

        return "보호자에게 저혈당 지속 알림 발송 완료";
    }

    // 고혈당
    public String sendHighProtector(Long protectorId,Long childId) {
        User protector = userRepository.findById(protectorId).get();
        User child = userRepository.findById(childId).get();
        Notification notification = Notification.builder()
                .title("이상 혈당 지속 알림")
                .message("'" + child.getNickname() + "'" + "의 고혈당 증상 15분 이상 지속 중.")
                .receiver(protector)
                .child(child)
                .build();
        pushNotificationService.sendPushNotification(protector, notification, PushLog.Category.blood_sugar);

        return "보호자에게 고혈당 지속 알림 발송 완료";
    }
}
