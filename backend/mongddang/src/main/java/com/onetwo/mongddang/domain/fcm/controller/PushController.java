package com.onetwo.mongddang.domain.fcm.controller;

import com.onetwo.mongddang.domain.fcm.service.push.GlycemiaChildService;
import com.onetwo.mongddang.domain.fcm.service.push.GlycemiaProtectorService;
import com.onetwo.mongddang.domain.fcm.service.push.SendMedicationService;
import com.onetwo.mongddang.domain.fcm.service.push.SleepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/push/bp")
@RequiredArgsConstructor
@Tag(name = "Send Push API", description = "push 알림을 보내는 api")
public class PushController {

    private final GlycemiaChildService glycemiaChildService;
    private final GlycemiaProtectorService glycemiaProtectorService;
    private final SendMedicationService medicationService;
    private final SleepService sleepService;

    @PostMapping("/sendglycemiachild/{childId}/low")
    @Operation(summary = "이상혈당 어린이 push 알림", description = "어린이에게 저혈당 알림을 보냅니다.")
    public String sendGlycemiaLowChild(@PathVariable Long childId) {
        return glycemiaChildService.sendLowChild(childId);
    }

    @PostMapping("/sendglycemiachild/{childId}/high")
    @Operation(summary = "이상혈당 어린이 push 알림", description = "어린이에게 고혈당 알림을 보냅니다.")
    public String sendGlycemiaHighChild(@PathVariable Long childId) {
        return glycemiaChildService.sendHighChild(childId);
    }

    @PostMapping("/sendglycemiaprotector/{protectorId}/{childId}/low")
    @Operation(summary = "이상혈당 보호자 push 알림", description = "저혈당이 지속되었으므로 연결된 보호자들에게 알림을 보냅니다.")
    public String sendGlycemialowProtector(@PathVariable Long protectorId,@PathVariable Long childId) {
        return glycemiaProtectorService.sendLowProtector(protectorId, childId);
    }

    @PostMapping("/sendglycemiaprotector/{protectorId}/{childId}/high")
    @Operation(summary = "이상혈당 보호자 push 알림", description = "고혈당이 지속되었으므로 연결된 보호자들에게 알림을 보냅니다.")
    public String sendGlycemiaHighProtector(@PathVariable Long protectorId,@PathVariable Long childId) {
        return glycemiaProtectorService.sendHighProtector(protectorId, childId);
    }

    @PostMapping("/sendmedication/{userId}")
    @Operation(summary = "복약시간 push 알림", description = "복약시간 알림을 보냅니다.")
    public String sendMedication(@PathVariable Long userId) {
        return medicationService.sendMedication(userId);
    }

    @PostMapping("/sendsleep/{userId}")
    @Operation(summary = "수면시간 push 알림", description = "수면시간 알림을 보냅니다.")
    public String sendSleep(@PathVariable Long userId) {
        return sleepService.sendSleep(userId);
    }
}
