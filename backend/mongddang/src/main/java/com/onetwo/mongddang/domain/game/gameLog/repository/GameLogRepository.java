package com.onetwo.mongddang.domain.game.gameLog.repository;

import com.onetwo.mongddang.domain.game.gameLog.model.GameLog;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Long> {

    Optional<GameLog> findById(Long Id);

    Optional<GameLog> findTopByChildIdOrderByIdDesc(@Param("childId") Long childId);

    Optional<GameLog> findTopByChildOrderByIdDesc(User child);

    boolean existsByChild(User child);
}
