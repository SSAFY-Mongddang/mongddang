package com.onetwo.mongddang.domain.mealplan.repository;

import com.onetwo.mongddang.domain.mealplan.model.Mealplan;
import com.onetwo.mongddang.domain.user.model.CtoP;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface MealplanRepository extends JpaRepository<Mealplan, Long> {

    @Query(value = "SELECT * FROM mealplan m " +
            "WHERE m.child_id = :childId " +
            "AND m.meal_time = :mealTime " +
            "AND m.start_time >= CURRENT_DATE " +
            "AND m.start_time < CURRENT_DATE + INTERVAL 1 DAY " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Mealplan> findOneByChildIdAndMealTimeToday(
            @Param("childId") Long childId,
            @Param("mealTime") String mealTime);


    @Query("SELECT m FROM Mealplan m WHERE m.child = :child ORDER BY m.startTime DESC LIMIT 1")
    Optional<Mealplan> findFirstByChildOrderByStartTimeDesc(@Param("child") User child);

}
