package com.mongddang.app

import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataTypes

object AppConstants {
    // 권한 키 정의
    const val BLOOD_GLUCOSE = "bloodglucose"
    const val STEPS = "steps"
    const val SLEEP = "sleep"
    const val BLOOD_OXYGEN = "bloodoxygen"
    const val SKIN_TEMPERATURE = "skintemperature"
    const val NUTRITION = "nutrition"
    const val HEART_RATE = "heartrate"

    // 권한 키와 Permission 매핑
    val PERMISSION_MAPPING: Map<String, Permission> = mapOf(
        BLOOD_GLUCOSE to Permission.of(DataTypes.BLOOD_GLUCOSE, AccessType.READ),
        STEPS to Permission.of(DataTypes.STEPS, AccessType.READ),
        SLEEP to Permission.of(DataTypes.SLEEP, AccessType.READ),
        BLOOD_OXYGEN to Permission.of(DataTypes.BLOOD_OXYGEN, AccessType.READ),
        SKIN_TEMPERATURE to Permission.of(DataTypes.SKIN_TEMPERATURE, AccessType.READ),
        NUTRITION to Permission.of(DataTypes.NUTRITION, AccessType.READ),
        HEART_RATE to Permission.of(DataTypes.HEART_RATE, AccessType.READ)
    )

    // 상태 상수
    const val SUCCESS = "SUCCESS"
    const val WAITING = "WAITING"
    const val NO_PERMISSION = "NO PERMISSION"
}