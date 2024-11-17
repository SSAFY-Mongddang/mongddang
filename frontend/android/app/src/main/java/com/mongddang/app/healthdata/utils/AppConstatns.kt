package com.mongddang.app.healthdata.utils

import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataTypes
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime
import java.time.LocalTime

object AppConstants {
    // 권한 키 정의
    const val BLOOD_GLUCOSE = "bloodglucose"
    const val STEPS = "steps"
    const val SLEEP = "sleep"
    const val HEART_RATE = "heartrate"

    val minimumDate: LocalDateTime = LocalDateTime.of(1900, 1, 1, 0, 0)
    val currentDate: LocalDateTime = LocalDateTime.now().with(LocalTime.MIDNIGHT)
    val SCOPE_IO_DISPATCHERS = Dispatchers.IO
    val TIME_INTERVAL = 5


    // 권한 키와 Permission 매핑
    val PERMISSION_MAPPING: Map<String, Permission> = mapOf(
        BLOOD_GLUCOSE to Permission.of(DataTypes.BLOOD_GLUCOSE, AccessType.READ),
        STEPS to Permission.of(DataTypes.STEPS, AccessType.READ),
        SLEEP to Permission.of(DataTypes.SLEEP, AccessType.READ),
        HEART_RATE to Permission.of(DataTypes.HEART_RATE, AccessType.READ)
    )

    // 상태 상수
    const val SUCCESS = "SUCCESS"
    const val WAITING = "WAITING"

    fun findKeyByInsensitiveValue(value: String): String? {
        return PERMISSION_MAPPING.keys.firstOrNull { it.equals(value, ignoreCase = true) }
    }
}