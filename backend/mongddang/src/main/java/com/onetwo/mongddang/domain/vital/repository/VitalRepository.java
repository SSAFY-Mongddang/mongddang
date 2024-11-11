package com.onetwo.mongddang.domain.vital.repository;

import com.onetwo.mongddang.domain.vital.model.Vital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VitalRepository extends JpaRepository<Vital,Long> {
    @Query("SELECT e FROM Vital e WHERE function('date', e.measurementTime) = :date AND e.child.id = :childId")
    List<Vital> findAllByDateAndChildId(@Param("date") LocalDate date, @Param("childId") Long childId);

    @Query("SELECT v FROM Vital v WHERE v.measurementTime BETWEEN :start AND :end AND v.status IN (Vital.GlucoseStatusType.low, Vital.GlucoseStatusType.high)")
    List<Vital> findByMeasurementTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT v.child FROM Vital v WHERE v.measurementTime BETWEEN :start AND :end AND v.status IN (Vital.GlucoseStatusType.low, Vital.GlucoseStatusType.high) " +
            "GROUP BY v.child HAVING COUNT(v) >= 3")
    List<Vital> findPersistentAbnormalVitals(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
