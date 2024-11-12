package com.onetwo.mongddang.domain.record.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.utils.DateTimeUtils;
import com.onetwo.mongddang.domain.record.dto.record.OngoingResponseDto;
import com.onetwo.mongddang.domain.record.dto.record.RecordDetailsDto;
import com.onetwo.mongddang.domain.record.dto.record.RecordWithChildIdDto;
import com.onetwo.mongddang.domain.record.dto.record.ResponseRecordDto;
import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.repository.RecordRepository;
import com.onetwo.mongddang.domain.user.application.CtoPUtils;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final CtoPUtils ctoPUtils;
    private final DateTimeUtils dateTimeUtils;


    /**
     * 환아의 활동 기록 조회
     *
     * @param userId    조회를 시도하는 사용자 아이디
     * @param nickname  조회 대상의 닉네임
     * @param startDate 조회 시작 월 (YYYY-MM)
     * @param endDate   조회 종료일 월 (YYYY-MM)
     * @return ResponseDto
     */
    public ResponseDto getRecord(Long userId, String nickname, String startDate, String endDate) {
        log.info("getRecord userId: {}", userId);

        // 요청자의 정보
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("user: {}", user.getEmail());

        // 환아의 정보
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("child: {}", child.getEmail());

        // 보호자가 어린이의 데이터에 쓰기 권한이 있는지 확인
        ctoPUtils.validateProtectorAccessChildData(user, child);

        // 시작일과 종료일을 LocalDateTime 으로 변환
        LocalDateTime[] dateTimes = dateTimeUtils.convertToDateTimes(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];
        log.info("startDateTime: {}, endDateTime: {}", startDateTime, endDateTime);

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

        // 최종 결과를 DTO로 변환
        List<ResponseRecordDto> dateRecords = recordsByDate.entrySet().stream()
                .map(entry -> new ResponseRecordDto(entry.getKey(), entry.getValue()))
                .toList();

        return ResponseDto.builder()
                .code(200)
                .message("기록 목록 조회에 성공했습니다.")
                .data(Collections.singletonMap("dates", dateRecords))
                .build();
    }


    /**
     * 진행 중인 식사 기록 조회
     *
     * @param childId 현재 진행 중인 식사 기록을 조회할 어린이의 아이디
     * @return ResponseDto
     */
    public ResponseDto findOngoingRecord(Long childId) {
        log.info("findOngoingRecord childId: {}", childId);

        // 어린이 정보 조회
        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        Optional<Record> ongoingRecord = Optional.empty();

        // 가장 최근에 시작된 식사/운동/수면 기록 조회
        Optional<Record> lastedMealRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, meal);
        Optional<Record> lastedExerciseRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, exercise);
        Optional<Record> lastedSleepRecord = recordRepository.findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(child, sleeping);

        if (lastedMealRecord.isPresent()) {
            ongoingRecord = lastedMealRecord;
        } else if (lastedExerciseRecord.isPresent()) {
            ongoingRecord = lastedExerciseRecord;
        } else if (lastedSleepRecord.isPresent()) {
            ongoingRecord = lastedSleepRecord;
        }

        // 진행중인 기록 확인
        if (ongoingRecord.isEmpty()) {
            return ResponseDto.builder()
                    .message("진행 중인 기록이 없습니다.")
                    .build();
        }

        OngoingResponseDto ongoingRecordResponse = OngoingResponseDto.builder()
                .category(ongoingRecord.get().getCategory())
                .startTime(ongoingRecord.get().getStartTime())
                .build();

        // 진행 중인 기록의 시작 시간 반환
        return ResponseDto.builder()
                .message("진행중인 기록을 조회합니다.")
                .data(ongoingRecordResponse)
                .build();
    }
}
