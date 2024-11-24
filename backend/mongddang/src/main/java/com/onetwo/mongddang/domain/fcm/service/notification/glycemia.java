//package com.onetwo.mongddang.domain.fcm.service.notification;
//
//import com.onetwo.mongddang.domain.fcm.dto.Notification;
//import com.onetwo.mongddang.domain.fcm.model.PushLog;
//import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
//import com.onetwo.mongddang.domain.user.model.CtoP;
//import com.onetwo.mongddang.domain.user.model.User;
//import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
//import com.onetwo.mongddang.domain.vital.model.Vital;
//import com.onetwo.mongddang.domain.vital.repository.VitalRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class glycemia {
//
//    private final VitalRepository vitalRepository;
//    private final PushNotificationService pushNotificationService;
//    private final CtoPRepository ctoPRepository;
//
//    // 어린이 : 최초 혈당 이상 시기부터 해결 될 때끼지 5분 간격으로
//    @Scheduled(cron = "0 * * * * *") // 매 분마다 실행
//    public void sendMedicationReminders() {
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime fiveMinutesAgo = now.minusMinutes(1); // 1분 전
//        // 지난 5분간의 혈당 데이터 조회
//        List<Vital> vitals = vitalRepository.findByMeasurementTimeBetween(fiveMinutesAgo, now);
//
//        for (Vital vital : vitals) {
//            if (vital.getStatus() == Vital.GlucoseStatusType.low || vital.getStatus() == Vital.GlucoseStatusType.high) {
//                User child = vital.getChild();
//                String message = (vital.getStatus() == Vital.GlucoseStatusType.low ? "저혈당" : "고혈당") + "증상이 나타나고 있어요.";
//                Notification notification = Notification.builder()
//                        .title("이상 혈당 알림")
//                        .message(message)
//                        .receiver(child)
//                        .child(child)
//                        .medicationId("이상혈당")
//                        .build();
//                pushNotificationService.sendPushNotification(child, notification, PushLog.Category.blood_sugar);
//
//                List<User> protectors = ctoPRepository.findProtectorByChildId(child.getId());
//
//                for (User protector : protectors) {
//                    Notification notification_protector = Notification.builder()
//                            .title("이상 혈당 알림")
//                            .message("'" + child.getNickname() + "'" + "의" + (vital.getStatus() == Vital.GlucoseStatusType.low ? "저혈당" : "고혈당")+" 발생!")
//                            .receiver(protector)
//                            .child(child)
//                            .medicationId("이상혈당")
//                            .build();
//                    pushNotificationService.sendPushNotification(protector, notification_protector, PushLog.Category.blood_sugar);
//                }
//            }
//        }
//    }
//
//    // 부모 : 15분 이상 해결되지 않을 때 알림 (어린이에게는 알리지 않음)
//    @Scheduled(fixedRate = 5 * 60 * 1000) // 5분마다 실행
//    public void checkPersistentBloodSugarLevel() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime fifteenMinutesAgo = now.minusMinutes(15);
//
//        // 지난 15분 동안 저혈당/고혈당 상태인 Vital 데이터
//        List<Vital> vitals = vitalRepository.findPersistentAbnormalVitals(fifteenMinutesAgo, now);
//
//        for (Vital vital : vitals) {
//            User child = vital.getChild();
//            String message = (vital.getStatus() == Vital.GlucoseStatusType.low ? "저혈당" : "고혈당") + " 증상이 15분 이상 지속되고 있습니다.";
//
//            // 보호자 목록 가져오기
//            List<CtoP> relations = ctoPRepository.findByChild(child);
//            for (CtoP relation : relations) {
//                User protector = relation.getProtector();
//                Notification notification = Notification.builder()
//                        .title("이상 혈당 지속 알림")
//                        .message(message)
//                        .receiver(protector)
//                        .child(child)
//                        .medicationId("이상혈당")
//                        .build();
//                pushNotificationService.sendPushNotification(child, notification, PushLog.Category.blood_sugar);;
//            }
//        }
//    }
//}
