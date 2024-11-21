package com.mongddang.app.data.local.converters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val formattedDate = Converters.dateToString(value) // Converters 메서드 활용
        encoder.encodeString(formattedDate ?: "")
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val dateString = decoder.decodeString()
        return Converters.fromString(dateString) // Converters 메서드 활용
            ?: throw IllegalArgumentException("Invalid date format: $dateString")
    }
}