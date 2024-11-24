package com.onetwo.mongddang.domain.fcm.service.notification;

import com.onetwo.mongddang.domain.fcm.dto.Notification;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.medication.model.MedicationTime;
import com.onetwo.mongddang.domain.medication.repository.MedicationManagementRepository;
import com.onetwo.mongddang.domain.medication.repository.MedicationTimeRepository;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.record.model.Record;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class Medication {

    private final RecordRepository recordRepository;
    private final PushNotificationService pushNotificationService;
    private final MedicationTimeRepository medicationTimeRepository;
    private final MedicationManagementRepository medicationManagementRepository;

    @Scheduled(cron = "0 * * * * *") // 매 분마다 실행
    public void sendMedicationReminders() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        List<MedicationTime> medicationTimes = medicationTimeRepository.findMedicationTimesByCurrentTime(now);

        for (MedicationTime medicationTime : medicationTimes) {
            User child = medicationTime.getMedicationManagement().getChild();
            Long medicationId = medicationTime.getMedicationManagement().getId();
            log.info("medi : " + medicationId.toString());

            String message = "인슐린을 맞을 시간이에요.";
            Notification notification = Notification.builder()
                    .title("복약 알림")
                    .message(message)
                    .receiver(child)
                    .child(child)
                    .medicationId(medicationId.toString())
                    .build();
            pushNotificationService.sendPushNotification(child, notification, PushLog.Category.medication);
        }
    }
}
