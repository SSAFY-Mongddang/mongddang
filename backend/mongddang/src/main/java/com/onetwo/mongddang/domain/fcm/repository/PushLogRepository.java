package com.onetwo.mongddang.domain.fcm.repository;

import com.onetwo.mongddang.domain.fcm.model.PushLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushLogRepository extends JpaRepository<PushLog, Long> {
    List<PushLog> findAllByUserId(Long userId);

    Boolean existsByUserIdAndIsNewTrue(Long userId);
}
