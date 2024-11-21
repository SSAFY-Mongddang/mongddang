package com.mongddang.app.data.local.dao.api

import com.mongddang.app.data.local.converters.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class BloodGlucoseResponse(
    @SerialName("id")
    val id: Long?= 0L,
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0,
    @Serializable(with = LocalDateTimeSerializer::class) // 커스텀 직렬화기
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null,
    @SerialName("status")
    val status: Status? = null,
    @SerialName("notification")
    val notification: Boolean? = false
)
