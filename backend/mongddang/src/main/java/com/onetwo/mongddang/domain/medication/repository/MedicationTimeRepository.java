package com.onetwo.mongddang.domain.medication.repository;

import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.medication.model.MedicationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicationTimeRepository extends JpaRepository<MedicationTime, Long> {

    List<MedicationTime> findByMedicationManagement(MedicationManagement medicationManagement);


    // Modifying :
    // 수정 작업 식별 - @Modifying을 사용하면 Spring Data JPA가 해당 메서드가 결과 집합을 반환하지 않는 수정 쿼리임을 인식합니다. 이는 SELECT 쿼리와 구분되는 중요한 점입니다.
    // nativeQuery :
    // Spring Data JPA가 JPQL 대신 네이티브 SQL 쿼리를 실행하도록 지시할 수 있습니다. 이 속성은 @Query 어노테이션의 필수 속성은 아닙니다.
    // @Query(value = "DELETE FROM medication_time mt WHERE mt.medication_management_id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    @Query("DELETE FROM MedicationTime mt WHERE mt.medicationManagement.id = :id")
    void deleteByMedicationManagementId(@Param("id") Long id);


    List<MedicationTime> findByMedicationManagementId(Long id);

    @Query("SELECT mt FROM MedicationTime mt " +
            "JOIN mt.medicationManagement mm " +
            "WHERE DATE(mm.repeatStartTime) = DATE(:now) " +
            "AND FUNCTION('TIME_FORMAT', mt.medicationTime, '%H:%i') = FUNCTION('TIME_FORMAT', :now, '%H:%i')")
    List<MedicationTime> findMedicationTimesByCurrentTime(@Param("now") LocalDateTime now);
}
