package com.onetwo.mongddang.domain.game.mongddang.repository;

import com.onetwo.mongddang.domain.game.mongddang.model.Mongddang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongddangRepository extends JpaRepository<Mongddang, Long> {
}
