package com.onetwo.mongddang.domain.record.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.gameLog.application.GameLogUtils;
import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.medication.dto.MedicationStandardDto;
import com.onetwo.mongddang.domain.medication.errors.CustomMedicationErrorCode;
import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.medication.model.MedicationTime;
import com.onetwo.mongddang.domain.medication.repository.MedicationManagementRepository;
import com.onetwo.mongddang.domain.medication.repository.MedicationTimeRepository;
import com.onetwo.mongddang.domain.missionlog.application.MissionLogUtils;
import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.record.dto.medication.MedicationDayInfo;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.domain.vital.dto.ResponseDailyGlucoseDto;
import com.onetwo.mongddang.domain.vital.service.VitalService;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.medication;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordMedicationService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final MissionLogUtils missionLogUtils;
    private final GameLogUtils gameLogUtils;
    private final VitalService vitalService;
    private final MedicationManagementRepository medicationManagementRepository;
    private final MedicationTimeRepository medicationTimeRepository;


    /**
     * 복약 기록하기
     *
     * @param childId 복약 기록을 시도하는 어린이의 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto checkMedication(Long childId, Long medicationId) throws JsonProcessingException {
        log.info("childId: {}, medicationId: {}", childId, medicationId);

        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        MedicationManagement medicationManagement = medicationManagementRepository.findById(medicationId)
                .orElseThrow(() -> new RestApiException(CustomMedicationErrorCode.MEDICATION_NOT_FOUND));

        List<MedicationTime> medicationTimeList = medicationTimeRepository.findByMedicationManagement(medicationManagement);

        // 복약 시간 데이터를 MedicationStandardDto 객체로 변환
        List<MedicationStandardDto> medicationStandardDtoList = new ArrayList<>();
        for (MedicationTime medicationTime : medicationTimeList) {
            MedicationStandardDto medicationStandardDto = MedicationStandardDto.builder()
                    .minGlucose(medicationTime.getMinGlucose())
                    .maxGlucose(medicationTime.getMaxGlucose())
                    .volume(medicationTime.getVolume())
                    .build();

            medicationStandardDtoList.add(medicationStandardDto);
        }

        List<String> medicationTimeListString = new ArrayList<>();
        for (MedicationTime medicationTime : medicationTimeList) {
            medicationTimeListString.add(medicationTime.getMedicationTime());
        }

        String closetTime = "00:00";
        // 현재 이전의 시간 중에서 가장 가까운 복약 시간 찾기
        for (MedicationTime medicationTime : medicationTimeList) {
            LocalTime localTime = LocalTime.parse(medicationTime.getMedicationTime(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalDateTime medicationDateTime = LocalDateTime.of(LocalDate.now(), localTime);
            if (LocalDateTime.now().isAfter(medicationDateTime)) {
                closetTime = medicationTime.getMedicationTime().toString();
                break;
            }
        }
        log.info("closetTime: {}", closetTime);

        // 현재 혈당 조회
        ResponseDto currentBloodSugarDto = vitalService.getCurrentBloodSugar(child.getId(), child.getNickname());
        ResponseDailyGlucoseDto responseDailyGlucoseDto = (ResponseDailyGlucoseDto) currentBloodSugarDto.getData();
        Integer bloodSugarLevel = responseDailyGlucoseDto.getBloodSugarLevel();

        log.info("create volume");
        Long volume = 2L;
        if (medicationTimeListString.size() != 0) {
            for (MedicationTime medicationTime : medicationTimeList) {
                if (medicationTime.getMinGlucose() == null) {
                    break;
                }

                if (bloodSugarLevel <= medicationTime.getMaxGlucose() && bloodSugarLevel >= medicationTime.getMinGlucose()) {
                    volume = medicationTime.getVolume();
                    break;
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        log.info("create new medication record");
        MedicationDayInfo medicationDayInfo = MedicationDayInfo.builder()
                .name(medicationManagement.getName())
                .time(closetTime)
                .volume(volume)
                .route(medicationManagement.getRoute())
                .isRepeat(medicationManagement.getIsRepeated())
                .repeatDays(medicationManagement.getRepeatDays())
                .repeatStartTime(medicationManagement.getRepeatStartTime())
                .repeatEndTime(medicationManagement.getRepeatEndTime())
                .isFast(medicationManagement.getIsRepeated())
                .repeatTimes(medicationTimeListString)
                .standards(medicationStandardDtoList)
                .build();

        log.info("created new record");
        JsonNode jsonNode = objectMapper.valueToTree(medicationDayInfo);

        log.info("save new medication record");
        Record newMedicationRecord = Record.builder()
                .child(child)
                .category(medication)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .content(jsonNode)
                .imageUrl(null)
                .isDone(true)
                .mealTime(null)
                .build();

        recordRepository.save(newMedicationRecord);
        log.info("복약 확인 기록 완료. : {}", newMedicationRecord.getStartTime());

        // 미션 업데이트
        missionLogUtils.completeMission(child, MissionDto.Mission.first_medication);

        // 게임 로그 업데이트
        gameLogUtils.addGameLog(child, GameLog.GameLogCategory.medication_count);


        return ResponseDto.builder()
                .message("복약을 확인합니다.")
                .data(bloodSugarLevel)
                .build();
    }
}
