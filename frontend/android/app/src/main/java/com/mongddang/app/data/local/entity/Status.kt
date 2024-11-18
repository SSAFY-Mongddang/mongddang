package com.mongddang.app.data.local.entity

enum class Status {
    low, normal, high;

    companion object {
        fun isStatus(level: Int): Status {
            return when {
                level < 70 -> low
                level > 200 -> high
                else -> normal
            }
        }
    }
}