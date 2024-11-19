package com.onetwo.mongddang.domain.game.achievement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity // JPA Entity 로 사용
@Getter // Getter 자동 생성
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 업적 ID

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AchievementCategory category; // 업적 카테고리 (ex. eating/sleepinging/exercise/medication)

    @Column(nullable = false)
    private int count; // 획득에 필요한 횟수 (ex. 10)

    @Column(nullable = false)
    private String description; // 업적 획득 조건 설명 (ex. 제시간에 자기 10번 수행)

    public enum AchievementCategory {
        meal, sleeping, exercise, medication;
    }

}