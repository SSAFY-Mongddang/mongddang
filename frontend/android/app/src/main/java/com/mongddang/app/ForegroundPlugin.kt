package com.mongddang.app

import android.Manifest
import android.app.ForegroundServiceStartNotAllowedException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.Permission
import com.mongddang.app.utils.ServiceUtils


private const val TAG = "ForegroundPlugin"

@CapacitorPlugin(
    name = "Foreground",
    permissions = [
        Permission(
            strings = [Manifest.permission.FOREGROUND_SERVICE],
            alias = "foregroundService"
        )
    ]
)
class ForegroundPlugin : Plugin() {

    companion object {
        private const val FOREGROUND_SERVICE_PERMISSION_REQUEST_CODE = 1001
    }

    @PluginMethod
    fun startForeground(call: PluginCall) {
        val activity = activity ?: run {
            call.reject("Activity is null")
            return
        }

        val permissionStatus = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.FOREGROUND_SERVICE
        )

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.FOREGROUND_SERVICE),
                FOREGROUND_SERVICE_PERMISSION_REQUEST_CODE
            )
            call.reject("Permission required")
            return
        }
        if(ForegroundService.isRunning){
            Log.d(TAG, "startForeground: 이미 동작중입니다.")
            call.reject("ForegroundService is already working")
            return
        }
        try {
            // 포그라운드 서비스 시작 로직을 여기에 구현합니다.
            val intent = Intent(context, ForegroundService::class.java)
            context.startService(intent)
            call.resolve(JSObject().put("message", "Foreground service started"))
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && e is ForegroundServiceStartNotAllowedException
            ) {
                call.reject("Foreground service start not allowed", e)
                Log.e(TAG, "Foreground service start not allowed", e)
            } else {
                call.reject("Unknown error occurred", e)
                Log.e(TAG, "Unknown error occurred", e)
            }
        }
    }

    @PluginMethod
    fun stopForeground(call: PluginCall) {
        if(ForegroundService.isRunning){
            Log.d(TAG, "stopForeground: 동작")
            val intent = Intent(context, ForegroundService::class.java)
            context.stopService(intent)
            call.resolve(JSObject().put("message", "Foreground service stop"))
        } else{
            Log.d(TAG, "stopForeground: 현재 모니터링 하는 중이 아님.")
            call.resolve(JSObject().put("message", "Foreground service is not working now"))
        }
    }

    @PluginMethod
    fun isCheckStatusMonitoring(call: PluginCall){
        val isActive = ServiceUtils.isServiceRunning(context, ForegroundService::class.java)
        if(isActive){
            call.resolve(JSObject().put("isActive", isActive))
        } else {
            call.resolve(JSObject().put("isActive", isActive))
        }
    }
}