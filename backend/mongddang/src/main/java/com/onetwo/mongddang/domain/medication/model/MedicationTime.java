package com.onetwo.mongddang.domain.medication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medication_time")
public class MedicationTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false)
    private MedicationManagement medicationManagement;

    @Column(name = "medication_time")
    private String medicationTime;

    @NotNull
    @Column(name = "is_fast", nullable = false)
    private Boolean isFast;

    @Column(name = "min_glucose")
    private Long minGlucose;

    @Column(name = "max_glucose")
    private Long maxGlucose;

    @NotNull
    private Long volume;
}
