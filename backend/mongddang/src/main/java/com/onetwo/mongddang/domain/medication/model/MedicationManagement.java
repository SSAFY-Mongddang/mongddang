package com.onetwo.mongddang.domain.medication.model;

import com.onetwo.mongddang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medication_management")
public class MedicationManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User child;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private RouteType route;

    @Column(name = "is_repeated")
    private Boolean isRepeated;

    @Column(name = "repeat_days")
    private List<String> repeatDays;

    @NotNull
    @Column(name = "repeat_start_time")
    private LocalDateTime repeatStartTime;

    @NotNull
    @Column(name = "repeat_end_time")
    private LocalDateTime repeatEndTime;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    public enum RouteType {
        mouth, injection
    }

}
