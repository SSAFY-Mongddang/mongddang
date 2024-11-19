package com.onetwo.mongddang.domain.medication.repository;

import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationManagementRepository extends JpaRepository<MedicationManagement, Long> {
    List<MedicationManagement> findByChild(User child);

//    // 오늘이 포함된 medication_management 리스트 조회
//    @Query("SELECT mm FROM MedicationManagement mm WHERE mm.child.id = :child AND mm.repeat_start_time <= :todayStart AND mm.repeat_end_time >= :todayStart", )
//    List<MedicationManagement> findByChildIdAndTodayIncluded(@Param("child") User child, @Param("todayStart") LocalDateTime todayStart);
}
