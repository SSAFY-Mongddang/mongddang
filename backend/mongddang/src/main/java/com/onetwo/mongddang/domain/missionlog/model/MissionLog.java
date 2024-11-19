package com.onetwo.mongddang.domain.missionlog.model;

import com.onetwo.mongddang.domain.missionlog.dto.MissionDto;
import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mission_log")
public class MissionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User child;

    @CreatedDate
    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MissionDto.Mission category;

    @NotNull
    private Long reward;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MissionDto.Status status;


}
