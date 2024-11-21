package com.mongddang.app.data.local.dao.api

enum class Status {
    low, normal, high;
    companion object {
        fun isStatus(bloodSugarLevel: Int): Status {
            return when {
                bloodSugarLevel < 70 -> low
                bloodSugarLevel > 140 -> high
                else -> normal
            }
        }
    }
}