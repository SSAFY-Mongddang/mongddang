package com.onetwo.mongddang.domain.record.application;

import com.onetwo.mongddang.domain.record.dto.record.RecordDetailsDto;
import com.onetwo.mongddang.domain.record.dto.record.RecordWithChildIdDto;
import com.onetwo.mongddang.domain.record.dto.record.ResponseRecordDto;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordUtils {


    private final RecordRepository recordRepository;


    public List<ResponseRecordDto> getRecordDateListBStartTimeBetween(User child, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        log.info("getRecordListBStartTimeBetween childId: {}, startTime: {}, endTime: {}", child.getEmail(), startDateTime, endDateTime);

        // 활동 기록을 조회할 때, 시작일과 종료일을 받아 해당 기간의 활동 기록을 조회
        List<Record> recordList = recordRepository.findByChildAndStartTimeBetween(child, startDateTime, endDateTime);
        // 날짜별로 기록을 그룹화
        Map<String, RecordDetailsDto> recordsByDate = new HashMap<>();

        for (Record record : recordList) {
            String recordDate = record.getStartTime().toLocalDate().toString(); // 날짜 추출

            recordsByDate.putIfAbsent(recordDate, new RecordDetailsDto(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

            RecordDetailsDto details = recordsByDate.get(recordDate);

            RecordWithChildIdDto recordWithChildIdDto = RecordWithChildIdDto.builder()
                    .id(record.getId())
                    .childId(child.getId())
                    .category(record.getCategory())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .content(record.getContent())
                    .imageUrl(record.getImageUrl())
                    .isDone(record.getIsDone())
                    .mealTime(record.getMealTime())
                    .build();

            // 각 기록의 종류에 따라 분류
            if (record.getCategory().equals(meal)) {
                details.getMeal().add(recordWithChildIdDto);
            } else if (record.getCategory().equals(exercise)) {
                details.getExercise().add(recordWithChildIdDto);
            } else if (record.getCategory().equals(sleeping)) {
                details.getSleep().add(recordWithChildIdDto);
            } else if (record.getCategory().equals(medication)) {
                details.getMedication().add(recordWithChildIdDto);
            }
        }

        // 최종 결과를 DTO 로 변환
        return recordsByDate.entrySet().stream()
                .map(entry -> new ResponseRecordDto(entry.getKey(), entry.getValue()))
                .toList();
    }


}
