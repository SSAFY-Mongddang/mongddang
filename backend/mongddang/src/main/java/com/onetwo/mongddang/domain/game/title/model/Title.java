package com.onetwo.mongddang.domain.game.title.model;

import com.onetwo.mongddang.domain.game.achievement.model.Achievement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity // JPA Entity 로 사용
@Getter // Getter 자동 생성
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 칭호 ID

    @OneToOne // 하나의 Title 이 하나의 Achievement 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private Achievement achievement; // 연결된 업적

    @Column(nullable = false)
    private String name; // 칭호 이름 (ex. 수면 마스터)

}
