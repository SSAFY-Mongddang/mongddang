package com.onetwo.mongddang.domain.game.coinLog.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA Entity로 사용
@Getter // Getter 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
@Builder // 빌더 패턴
public class CoinLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 재화 로그 ID

    @ManyToOne // 여러 CoinLog가 하나의 ChildUser에 연결
    @JoinColumn(nullable = false) // 외래키 설정
    private User child; // 연결된 어린이 유저

    @Column(nullable = false)
    @NotNull
    private int coin; // 재화 획득량 (ex. 100)

    @Column(nullable = false)
    @NotNull // 빌드할 때 null 체크, 소문자 그대로 들어감
    @Enumerated(EnumType.STRING)
    private CoinCategory category; // 획득 경로 (ex. achievement/mission/mongddang/)

    public enum CoinCategory {
        achievement, mission, mongddang;
    }
}
