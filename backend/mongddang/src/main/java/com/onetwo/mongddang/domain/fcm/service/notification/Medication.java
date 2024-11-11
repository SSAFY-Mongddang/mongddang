package com.onetwo.mongddang.domain.fcm.service.notification;

import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.service.PushNotificationService;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.record.model.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Medication {

    private final RecordRepository recordRepository;
    private final PushNotificationService pushNotificationService;

    @Scheduled(cron = "0 * * * * *") // 매 분마다 실행
    public void sendMedicationReminders() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        List<Record> records = recordRepository.findByCategoryAndStartTime(Record.RecordCategoryType.medication, now);

        for (Record record : records) {
            User child = record.getChild();
            String message = "복약 시간입니다.";
            pushNotificationService.sendPushNotification(child, "복약 알림", message, PushLog.Category.medication);
        }
    }
}
