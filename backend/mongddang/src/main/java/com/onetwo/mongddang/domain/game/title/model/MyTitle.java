package com.onetwo.mongddang.domain.game.title.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity // JPA Entity 로 사용
@Getter // Getter 자동 생성
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MyTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 내 칭호 ID

    @ManyToOne // 여러 MyTitle 이 하나의 ChildUser 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private User child; // 연결된 어린이 유저

    @ManyToOne // 여러 MyTitle 이 하나의 Title 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private Title title; // 연결된 캐릭터

    @Column(nullable = false)
    private LocalDateTime createdAt; // 획득 시간 (ex. 2024-10-01 12:00:00)

    @Column(nullable = false)
    private Boolean isNew; // 신규 여부 (ex. true)

    @Column(nullable = false)
    private Boolean isMain; // 메인 여부 (ex. true)

}
