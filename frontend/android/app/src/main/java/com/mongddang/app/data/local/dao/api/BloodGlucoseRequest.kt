package com.mongddang.app.data.local.dao.api

import com.mongddang.app.data.local.converters.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class BloodGlucoseRequest(
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0,
    @Serializable(with = LocalDateTimeSerializer::class) // 커스텀 직렬화기
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null,
    @SerialName("packageName")
    val packageName: String? = null
    
){
    companion object {
        fun create(
            bloodSugarLevel: Int,
            measurementTime: LocalDateTime? = null,
            packageName: String? = null
        ): BloodGlucoseRequest {
            return BloodGlucoseRequest(
                bloodSugarLevel = bloodSugarLevel,
                measurementTime = measurementTime,
                packageName = packageName
            )
        }
    }
}