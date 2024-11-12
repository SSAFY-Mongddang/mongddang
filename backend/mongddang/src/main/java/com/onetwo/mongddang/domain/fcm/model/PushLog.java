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
@Builder(toBuilder = true)
@NoArgsConstructor // JPA용 기본 생성자
@AllArgsConstructor // Builder에서 모든 필드를 초기화하는 생성자
public class PushLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Boolean isNew;

    public enum Category {
        game,blood_sugar,medication
    };


}
