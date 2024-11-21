package com.onetwo.mongddang.domain.mealplan.repository;

import com.onetwo.mongddang.domain.mealplan.model.Mealplan;
import com.onetwo.mongddang.domain.user.model.CtoP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealplanRepository extends JpaRepository<Mealplan, Long> {
}
