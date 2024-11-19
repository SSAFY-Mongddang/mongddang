package com.onetwo.mongddang.domain.game.mongddang.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter // Getter 자동 생성
@Setter
@AllArgsConstructor // final 필드를 파라미터로 받는 생성자 생성
@NoArgsConstructor
@Builder // 빌더 패턴 자동 생성
public class MyMongddang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 캐릭터 ID (PK)

    @ManyToOne // 여러 MyCharacter 가 하나의 ChildUser 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private User child; // 연결된 어린이 유저

    @ManyToOne // 여러 MyCharacter 가 하나의 Character 에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private Mongddang mongddang; // 연결된 캐릭터

    @Column(nullable = false)
    private LocalDateTime createdAt; // 획득 시간 (ex. 2021-08-01 12:00:00)

    @Column(nullable = false)
    private Boolean isNew; // 신규 여부 (ex. true)

    @Column(nullable = false)
    private Boolean isMain; // 메인 여부 (ex. true)

}
