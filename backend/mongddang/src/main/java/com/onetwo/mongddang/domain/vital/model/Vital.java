package com.onetwo.mongddang.domain.vital.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User child;

    @Column(nullable = false, name = "blood_sugar_level")
    @NotNull
    private int bloodSugarLevel;

    @Column(nullable = false, name = "measurement_time")
    @NotNull
    private LocalDateTime measurementTime;

    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private GlucoseStatusType status;

    @Column(nullable = false, name = "is_notification")
    @NotNull
    private Boolean isNotification;

    public enum GlucoseStatusType {
        low, normal, high
    }


}
