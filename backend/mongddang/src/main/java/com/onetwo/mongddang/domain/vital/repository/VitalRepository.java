package com.onetwo.mongddang.domain.vital.repository;

import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.vital.model.Vital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VitalRepository extends JpaRepository<Vital, Long> {
    @Query("SELECT e FROM Vital e WHERE function('date', e.measurementTime) = :date AND e.child.id = :childId")
    List<Vital> findAllByDateAndChildId(@Param("date") LocalDate date, @Param("childId") Long childId);

    List<Vital> findByChild(User child);

    List<Vital> findByChildAndMeasurementTimeBetween(User child, LocalDateTime startDate, LocalDateTime endDate);

    Optional<Vital> findTopByChildOrderById(User child);
}
