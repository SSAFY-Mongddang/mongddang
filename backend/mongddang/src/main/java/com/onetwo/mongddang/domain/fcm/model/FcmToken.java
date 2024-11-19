package com.onetwo.mongddang.domain.fcm.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor // JPA용 기본 생성자
@AllArgsConstructor // Builder에서 모든 필드를 초기화하는 생성자
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @NotNull
    private String token;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateToken(String token) {
        this.token = token;
        this.updatedAt = LocalDateTime.now();
    }
}
