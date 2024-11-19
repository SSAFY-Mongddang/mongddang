package com.onetwo.mongddang.domain.game.gameLog.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity // JPA Entity 로 사용
@Getter // Getter 자동 생성
@Setter // Setter 자동 생성
@Builder // 빌더 패턴
@AllArgsConstructor
@NoArgsConstructor
public class GameLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게임 로그 ID

    @ManyToOne // 여러 GameLog 가 하나의 ChildUser 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private User child; // 연결된 어린이 유저

    @Column(nullable = false)
    private int mealCount; // 식사 횟수 (ex. 3)

    @Column(nullable = false)
    private int exerciseCount; // 운동 횟수 (ex. 2)

    @Column(nullable = false)
    private int sleepingCount; // 수면 횟수 (ex. 1)

    @Column(nullable = false)
    private int medicationCount; // 복약 횟수 (ex. 5)

    @NotNull
    public enum GameLogCategory {
        meal_count, sleeping_count, exercise_count, medication_count;
    }

}