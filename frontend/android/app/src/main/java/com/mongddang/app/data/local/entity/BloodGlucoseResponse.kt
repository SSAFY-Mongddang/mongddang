package com.mongddang.app.data.local.entity

import kotlinx.serialization.SerialName
import java.time.LocalDateTime

data class BloodGlucoseResponse(
    @SerialName("id")
    val id: Long?= 0L,
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0,
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null,
    @SerialName("status")
    val status: Status? = null,
    @SerialName("notification")
    val notification: Boolean? = false
)
