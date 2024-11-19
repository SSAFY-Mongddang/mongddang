package com.onetwo.mongddang.domain.game.title.repository;

import com.onetwo.mongddang.domain.game.title.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    Optional<Title> findByAchievementId(Long achievementId);
}
