package com.onetwo.mongddang.domain.game.mongddang.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter // Getter 자동 생성
@AllArgsConstructor // 생성자 자동 생성
@NoArgsConstructor
public class Mongddang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 캐릭터 ID (PK)

    @Column(nullable = false)
    private String name; // 캐릭터 이름 (ex. 말랑)

    @Column(nullable = false)
    private int price; // 가격 (ex. 1000)

    @Column(nullable = false)
    private String story; // 캐릭터 서사 (ex. 말랑은 무지개를 좋아해요)

}