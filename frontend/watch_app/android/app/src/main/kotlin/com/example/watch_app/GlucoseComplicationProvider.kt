package com.example.watch_app

import android.content.Context
import android.util.Log
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.LongTextComplicationData

class GlucoseComplicationProvider : ComplicationDataSourceService() {

    init {
        Log.d("WatchApp", "GlucoseComplicationProvider initialized")
    }

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        Log.d("WatchApp", "getPreviewData called with type: $type")
        return createComplicationData("--", type)
    }

    override fun onComplicationRequest(
        request: ComplicationRequest,
        listener: ComplicationRequestListener
    ) {
        Log.d("WatchApp", "onComplicationRequest started")

        val glucoseValue = getGlucoseValue()
        Log.d("WatchApp", "Retrieved glucose value: $glucoseValue")

        try {
            val complicationData = createComplicationData(glucoseValue ?: "--", request.complicationType)
            if (complicationData != null) {
                listener.onComplicationData(complicationData)
                Log.d("WatchApp", "Complication data sent successfully with value: $glucoseValue")
            }
        } catch (e: Exception) {
            Log.e("WatchApp", "Error creating complication data", e)
        }
    }

    private fun createComplicationData(value: String, type: ComplicationType): ComplicationData? {
        Log.d("WatchApp", "Creating complication data with value: $value for type: $type")
        return when (type) {
            ComplicationType.SHORT_TEXT -> {
                ShortTextComplicationData.Builder(
                    text = PlainComplicationText.Builder(text = value).build(),
                    contentDescription = PlainComplicationText.Builder(text = "혈당: $value").build()
                ).build()
            }
            ComplicationType.LONG_TEXT -> {
                LongTextComplicationData.Builder(
                    text = PlainComplicationText.Builder(text = "혈당: $value").build(),
                    contentDescription = PlainComplicationText.Builder(text = "혈당: $value").build()
                ).build()
            }
            else -> null
        }.also {
            Log.d("WatchApp", "Created complication data successfully")
        }
    }

    private fun getGlucoseValue(): String? {
        return applicationContext.getSharedPreferences("GlucoseData", Context.MODE_PRIVATE)
            .getString("glucose_value", null)
            .also { value ->
                Log.d("WatchApp", "Retrieved from SharedPreferences: $value")
            }
    }
}