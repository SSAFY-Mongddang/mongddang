package com.example.watch_app

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class WatchActivity : FlutterActivity() {
    private val CHANNEL = "com.example.watch_app"
    private val TAG = "WatchActivity"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "sendGlucoseUpdate" -> {
                    val glucoseValue = call.argument<String>("glucose_value")
                    val needsAlert = call.argument<Boolean>("needs_alert")
                    val alertType = call.argument<String>("alert_type")

                    Log.d(TAG, "Received update - glucose: $glucoseValue, needsAlert: $needsAlert, type: $alertType")

                    if (needsAlert == true) {
                        Log.d(TAG, "Triggering alert for type: $alertType")
                        when (alertType) {
                            "low" -> {
                                Log.d(TAG, "Triggering low alert")
                                triggerLowAlert()
                            }
                            "high" -> {
                                Log.d(TAG, "Triggering high alert")
                                triggerHighAlert()
                            }
                        }
                    }

                    updateWatchface(glucoseValue)
                    result.success(null)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    private fun triggerLowAlert() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            Log.d(TAG, "Vibrator service obtained: $vibrator")

            if (vibrator.hasVibrator()) {
                Log.d(TAG, "Device has vibrator capability")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "Using VibrationEffect for low alert")
                    val effect = VibrationEffect.createWaveform(longArrayOf(0, 300, 200, 300), -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 300, 200, 300), -1)
                }
                Log.d(TAG, "Low alert vibration triggered")
            } else {
                Log.e(TAG, "Device does not have vibrator capability")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error triggering vibration: ${e.message}", e)
        }
    }

    private fun triggerHighAlert() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            Log.d(TAG, "Vibrator service obtained: $vibrator")

            if (vibrator.hasVibrator()) {
                Log.d(TAG, "Device has vibrator capability")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "Using VibrationEffect for high alert")
                    val effect = VibrationEffect.createWaveform(longArrayOf(0, 1000), -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 1000), -1)
                }
                Log.d(TAG, "High alert vibration triggered")
            } else {
                Log.e(TAG, "Device does not have vibrator capability")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error triggering vibration: ${e.message}", e)
        }
    }

    private fun updateWatchface(glucoseValue: String?) {
        Log.d(TAG, "Updating watchface with value: $glucoseValue")
        // 워치페이스 업데이트 로직
    }
}