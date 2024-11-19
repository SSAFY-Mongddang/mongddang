package com.onetwo.mongddang.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DateTimeUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 시작일과 종료일을 LocalDateTime으로 변환
     *
     * @param startDate 시작일
     * @param endDate   종료일
     * @return LocalDateTime 배열 [시작일, 종료일]
     */
    public LocalDateTime[] convertToDateTimes(String startDate, String endDate) {
        // 시작일과 종료일을 LocalDate로 변환
        LocalDate startLocalDate = LocalDate.parse(startDate + "-01", formatter);
        LocalDate endLocalDate = LocalDate.parse(endDate + "-01", formatter);

        // LocalDateTime으로 변환 (시작일의 첫 날, 종료일의 마지막 날)
        LocalDateTime startDateTime = startLocalDate.atStartOfDay(); // 시작일의 첫 날 00:00:00
        LocalDateTime endDateTime = endLocalDate.withDayOfMonth(endLocalDate.lengthOfMonth()).atTime(23, 59, 59); // 종료일의 마지막 날 23:59:59

        return new LocalDateTime[]{startDateTime, endDateTime};
    }


}