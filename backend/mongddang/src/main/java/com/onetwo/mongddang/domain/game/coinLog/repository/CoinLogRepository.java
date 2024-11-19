package com.onetwo.mongddang.domain.game.coinLog.repository;

import com.onetwo.mongddang.domain.game.coinLog.model.CoinLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinLogRepository extends JpaRepository<CoinLog,Long> {

    Optional<CoinLog> findTopByChildId(@Param("childId") Long childId);

    Optional<CoinLog> findTopByChildIdOrderByIdDesc(@Param("childId") Long childId);
}
