package com.onetwo.mongddang.domain.game.achievement.repository;

import com.onetwo.mongddang.domain.game.achievement.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    // id로 Achievement 찾기
    Optional<Achievement> findById(Long id); // Optional : NullPointerException 을 방지 -> 반환된 값이 null 인지 확인하는 과정을 간소화

    // Achievement 전체 조회
    List<Achievement> findAll();

}
