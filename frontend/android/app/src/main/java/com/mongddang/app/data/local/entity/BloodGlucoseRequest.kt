package com.mongddang.app.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class BloodGlucoseRequest(
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0,
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null,
    @SerialName("status")
    val status: Status? = Status.isStatus(bloodSugarLevel),
    @SerialName("notification")
    val notification: Boolean? = false,
    @SerialName("packageName")
    val packageName: String? = null
)
