package com.mongddang.app.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
object Converters {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")


    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }
}