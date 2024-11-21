package com.onetwo.mongddang.domain.vital.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "blood_glucose")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BloodGlucose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User child;

    @Column(nullable = false, name = "blood_sugar_level")
    @NotNull
    private Integer bloodSugarLevel;

    @Column(nullable = false, name = "measurement_time")
    @NotNull
    private LocalDateTime measurementTime;

    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private GlucoseStatusType status;

    @Column(nullable = false, name = "is_notification")
    private Boolean isNotification;

    @Column(name="package_name")
    @NotNull
    private String packageName;
    public enum GlucoseStatusType {
        low, normal, high
    }
}
