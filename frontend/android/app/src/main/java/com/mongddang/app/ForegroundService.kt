package com.mongddang.app

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val TAG = "ForegroundService"

class ForegroundService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // 서비스 상태를 나타냄
    companion object {
        var isRunning = false 
    }

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        Log.i(TAG, "onCreate: ")
        createNotificationChannel()

        startForeground(1, createNotification())

        // 1분마다 모니터링 작업 시작
        startMonitoring()
    }
    private fun createNotificationChannel() {
        Log.i(TAG, "createNotificationChannel")
        // 알림 채널 생성
        val channel = NotificationChannel(
            "monitoring_channel",
            "Monitoring Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        Log.i(TAG, "createNotification")
        // 알림 생성
        return Notification.Builder(this, "monitoring_channel")
            .setContentTitle("모니터링 중")
            .setContentText("5분마다 상태를 확인하고 있습니다.")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        return START_STICKY // 서비스 종료 시 자동 재시작
    }

    // 1분마다 모니터링 작업을 실행하는 코루틴 함수
    private fun startMonitoring() {
        Log.i(TAG, "startMonitoring")
        serviceScope.launch {
            while (isActive) { // 서비스가 활성 상태일 동안 반복
                monitorStatus() // 모니터링 작업 실행
                delay(300000)    // 1분 대기 (1분 = 60000밀리초)
            }
        }
    }

    // 실제 모니터링 작업 수행 함수
    private fun monitorStatus() {
        val currentTime = LocalDateTime.now()
        // 모니터링 로직 (예: 서버 상태 확인, 데이터 동기화 등)
        Log.i(TAG, "시스템 상태를 모니터링 중 현재시각:${currentTime}")
        // 여기에 모니터링할 구체적인 로직을 추가할 수 있습니다.
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
        isRunning=false
        serviceScope.cancel() // 서비스가 종료될 때 모든 코루틴 작업 취소
    }

    override fun onBind(intent: Intent): IBinder? = null
}