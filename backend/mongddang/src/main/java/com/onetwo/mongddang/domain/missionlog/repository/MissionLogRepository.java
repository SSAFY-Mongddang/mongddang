package com.onetwo.mongddang.domain.missionlog.repository;

import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.missionlog.model.MissionLog;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MissionLogRepository extends JpaRepository<MissionLog, Long> {

    boolean existsByChildAndCreatedAtBetween(User child, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    List<MissionLog> findByChildAndCreatedAtBetween(User child, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Optional<MissionLog> findTopByChildAndCreatedAtBetweenAndCategoryIs(User child, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd, MissionDto.Mission mission);
}
