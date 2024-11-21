package com.onetwo.mongddang.domain.vital.repository;

import com.onetwo.mongddang.domain.vital.model.BloodGlucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodGlucoseRepository extends JpaRepository<BloodGlucose, Long> {
}
