package com.onetwo.mongddang.domain.record.repository;

import com.onetwo.mongddang.domain.record.model.Record;
import com.onetwo.mongddang.domain.record.model.Record.RecordCategoryType;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findTopByChildAndCategoryAndEndTimeIsNullOrderByIdDesc(User child, RecordCategoryType recordCategoryType);

    List<Record> findByChildAndStartTimeBetween(User child, LocalDateTime startDate, LocalDateTime endDate);

    Optional<Record> findTopByChildAndEndTimeIsNullOrderByIdDesc(User child);

}
