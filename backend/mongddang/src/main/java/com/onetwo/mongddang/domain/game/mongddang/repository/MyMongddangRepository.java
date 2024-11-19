package com.onetwo.mongddang.domain.game.mongddang.repository;

import com.onetwo.mongddang.domain.game.mongddang.model.MyMongddang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyMongddangRepository extends JpaRepository<MyMongddang, Long> {
    Optional<MyMongddang> findByMongddangId(Long id);

    Optional<MyMongddang> findByChildId(Long userId);

    Optional<MyMongddang> findByChildIdAndIsMainTrue(Long mongddangId);

    Optional<MyMongddang> findByMongddangIdAndChildId(Long mongddangId, Long childId);

}
