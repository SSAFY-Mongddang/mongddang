package com.example.watch_app

import android.content.ComponentName
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.ToneGenerator
import android.media.AudioManager
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
    private var mediaPlayer: MediaPlayer? = null
    private var toneGenerator: ToneGenerator? = null
    private lateinit var audioManager: AudioManager

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        initializeAudio()

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "sendGlucoseUpdate" -> {
                    val glucoseValue = call.argument<String>("glucose_value")
                    val needsAlert = call.argument<Boolean>("needs_alert")
                    val alertType = call.argument<String>("alert_type")

                    Log.d(TAG, "Received glucose value: $glucoseValue, needsAlert: $needsAlert, type: $alertType")

                    saveGlucoseValue(glucoseValue)
                    updateComplicationData()

                    if (needsAlert == true) {
                        // 알림 전에 볼륨을 최대로 설정
                        setMaxVolume()

                        when (alertType) {
                            "low" -> {
                                triggerLowAlert()
                                playLowAlert()
                            }
                            "high" -> {
                                triggerHighAlert()
                                playHighAlert()
                            }
                        }
                    }

                    result.success(null)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun initializeAudio() {
        try {
            audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            // ToneGenerator의 볼륨을 최대로 설정 (100은 최대값)
            toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing audio", e)
        }
    }

    private fun setMaxVolume() {
        try {
            // 알람 스트림의 최대 볼륨 가져오기
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)

            // 볼륨을 최대로 설정
            audioManager.setStreamVolume(
                AudioManager.STREAM_ALARM,
                maxVolume,
                0 // 볼륨 변경 시 소리/진동 피드백 없음
            )

            Log.d(TAG, "Volume set to maximum: $maxVolume")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting maximum volume", e)
        }
    }

    private fun playLowAlert() {
        try {
            // 저혈당 알림음: 두 번의 짧은 비프음 (최대 볼륨)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 300)
            Thread.sleep(500)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 300)
        } catch (e: Exception) {
            Log.e(TAG, "Error playing low alert sound", e)
        }
    }

    private fun playHighAlert() {
        try {
            // 고혈당 알림음: 한 번의 긴 비프음 (최대 볼륨)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 1000)
        } catch (e: Exception) {
            Log.e(TAG, "Error playing high alert sound", e)
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

    override fun onDestroy() {
        super.onDestroy()
        toneGenerator?.release()
        mediaPlayer?.release()
    }

    // 기존 코드의 나머지 부분은 그대로 유지
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