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
@Table(name="medication_time")
public class MedicationTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "medication_management_id")
    private MedicationManagement medicationManagement;

    @NotNull
    @Column(name="is_fast", nullable = false)
    private Boolean isFast;

    @Column(name="min_glucose")
    private int minGlucose;

    @Column(name="max_glucose")
    private int maxGlucose;

    @NotNull
    private Long volume;
}
