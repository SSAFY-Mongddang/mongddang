package com.onetwo.mongddang.domain.fcm.repository;

import com.onetwo.mongddang.domain.fcm.model.FcmToken;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUserId(Long userId);
    Optional<FcmToken> findByUser(User user);

}
