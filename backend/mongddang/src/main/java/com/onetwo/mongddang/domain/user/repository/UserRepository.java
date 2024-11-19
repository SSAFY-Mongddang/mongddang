package com.onetwo.mongddang.domain.user.repository;

import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.record.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByInvitationCode(String invitationCode);

    boolean existsByNickname(String nickname);

    Optional<User> findByInvitationCode(String InvitationCode);

    Optional<User> findByNickname(String nickname);

    // 오늘 오후에 시작하는 수면 기록이 없는 어린이 유저 리스트 뽑기
    @Query("SELECT u FROM User u WHERE u.role = 'child' AND " +
            "NOT EXISTS (SELECT r FROM Record r WHERE r.child = u AND r.startTime >= :startOfDay AND r.startTime < :endOfDay " +
            "AND r.category = 'sleeping')")
    List<User> findChildUsersWithoutSleepingRecordToday(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
