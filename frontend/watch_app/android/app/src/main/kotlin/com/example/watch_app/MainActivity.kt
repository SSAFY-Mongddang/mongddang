package com.example.watch_app

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.watch_app"
    private val TAG = "WatchApp"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "sendGlucoseUpdate" -> {
                    val glucoseValue = call.argument<String>("glucose_value")
                    val needsAlert = call.argument<Boolean>("needs_alert")
                    val alertType = call.argument<String>("alert_type")

                    Log.d(TAG, "Received glucose value: $glucoseValue, needsAlert: $needsAlert, type: $alertType")

                    saveGlucoseValue(glucoseValue)
                    updateComplicationData()

                    // 혈당 수치에 따른 진동 알림
                    if (needsAlert == true) {
                        when (alertType) {
                            "low" -> triggerLowAlert()
                            "high" -> triggerHighAlert()
                        }
                    }

                    result.success(null)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun triggerLowAlert() {
        try {
            val vibrator = getVibrator()
            Log.d(TAG, "Triggering low glucose alert vibration")

            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createWaveform(longArrayOf(0, 300, 200, 300), -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 300, 200, 300), -1)
                }
                Log.d(TAG, "Low alert vibration triggered successfully")
            } else {
                Log.e(TAG, "Device does not have vibrator capability")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error triggering low alert vibration", e)
        }
    }

    private fun triggerHighAlert() {
        try {
            val vibrator = getVibrator()
            Log.d(TAG, "Triggering high glucose alert vibration")

            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createWaveform(longArrayOf(0, 1000), -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 1000), -1)
                }
                Log.d(TAG, "High alert vibration triggered successfully")
            } else {
                Log.e(TAG, "Device does not have vibrator capability")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error triggering high alert vibration", e)
        }
    }

    private fun getVibrator(): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    private fun saveGlucoseValue(value: String?) {
        try {
            Log.d(TAG, "Attempting to save glucose value: $value")
            getSharedPreferences("GlucoseData", Context.MODE_PRIVATE)
                .edit()
                .putString("glucose_value", value)
                .apply()
            Log.d(TAG, "Successfully saved glucose value")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving glucose value", e)
        }
    }

    private fun updateComplicationData() {
        try {
            Log.d(TAG, "Starting complication update")
            val componentName = ComponentName(applicationContext, GlucoseComplicationProvider::class.java)
            Log.d(TAG, "Component name created: ${componentName.className}")

            val updateRequester = ComplicationDataSourceUpdateRequester.create(
                applicationContext,
                componentName
            )
            Log.d(TAG, "UpdateRequester created successfully")

            updateRequester.requestUpdateAll()
            Log.d(TAG, "Update request sent successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error updating complication", e)
        }
    }
}