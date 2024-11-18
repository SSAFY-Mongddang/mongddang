package com.mongddang.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "blood_glucose_data")
data class BloodGlucoseData (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val packageName: String,
    val time: LocalDateTime,
    val glucoseValue: Float
)