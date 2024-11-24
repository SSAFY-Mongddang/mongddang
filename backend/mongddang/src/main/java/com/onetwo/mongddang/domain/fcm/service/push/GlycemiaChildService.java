package com.onetwo.mongddang.domain.fcm.service.push;

import com.onetwo.mongddang.domain.fcm.dto.Notification;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GlycemiaChildService {

    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;
    private final CtoPRepository ctoPRepository;

    // 저혈당
    public String sendLowChild(Long userId) {
        User child = userRepository.findById(userId).get();

        Notification notification = Notification.builder()
                .title("이상 혈당 알림")
                .message("저혈당 증상이 나타나고 있어요.")
                .receiver(child)
                .child(child)
                .medicationId("이상혈당")
                .build();
        pushNotificationService.sendPushNotification(child, notification, PushLog.Category.blood_sugar);

        List<User> protectors = ctoPRepository.findProtectorByChildId(userId);

        for (User protector : protectors) {
            Notification notification_protector = Notification.builder()
                    .title("이상 혈당 알림")
                    .message("'" + child.getNickname() + "'" + "의 저혈당 발생!")
                    .receiver(protector)
                    .child(child)
                    .medicationId("이상혈당")
                    .build();
            pushNotificationService.sendPushNotification(protector, notification_protector, PushLog.Category.blood_sugar);
        }

        return "어린이에게 저혈당 알림 발송 완료";
    }

    // 고혈당
    public String sendHighChild(Long userId) {
        User child = userRepository.findById(userId).get();
        Notification notification = Notification.builder()
                .title("이상 혈당 알림")
                .message("고혈당 증상이 나타나고 있어요.")
                .receiver(child)
                .child(child)
                .medicationId("이상혈당")
                .build();
        pushNotificationService.sendPushNotification(child, notification, PushLog.Category.blood_sugar);

        List<User> protectors = ctoPRepository.findProtectorByChildId(userId);

        for (User protector : protectors) {
            Notification notification_protector = Notification.builder()
                    .title("이상 혈당 알림")
                    .message("'" + child.getNickname() + "'" + "의 고혈당 발생!")
                    .receiver(protector)
                    .child(child)
                    .medicationId("이상혈당")
                    .build();
            pushNotificationService.sendPushNotification(protector, notification_protector, PushLog.Category.blood_sugar);
        }

        return "어린이에게 고혈당 알림 발송 완료";
    }
}
