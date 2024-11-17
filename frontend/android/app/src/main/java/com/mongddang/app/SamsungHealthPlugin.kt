package com.mongddang.app

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SamsungHealthPlugin"

@CapacitorPlugin(name="SamsungHealthPlugin")
class SamsungHealthPlugin: Plugin() {

    private lateinit var healthMainViewModel: HealthMainViewModel

    override fun load() {
        super.load()
        healthMainViewModel = HealthViewModelFactory(context).create(HealthMainViewModel::class.java)
    }

    @PluginMethod
    fun requestHealthPermission(call: PluginCall) {
        val healthDataType = call.getString("healthDataType")?: run {
            call.reject("dataTypes parameter is required")
            return
        }
        val context = activity ?: run {
            call.reject("Activity context is null")
            return
        }
        val currentPermission = healthMainViewModel.mapToPermission(healthDataType)
        Log.d(TAG, "requestHealthPermission: $currentPermission")
        val checkPermission = healthMainViewModel.checkForPermission(context, healthDataType)
        Log.d(TAG, "requestHealthPermission: $checkPermission")
    }

    @PluginMethod
    fun checkPermissionStatusForHealthData(call: PluginCall) {
        val healthDataType = call.getString("healthDataType") ?: run {
            call.reject("healthDataType parameter is required")
            return
        }

        val context = activity ?: run {
            call.reject("Activity context is null")
            return
        }

        // 대소문자 무시한 키 찾기
        val key = AppConstants.findKeyByInsensitiveValue(healthDataType)
        if (key == null) {
            call.reject("Invalid healthDataType: $healthDataType")
            return
        }

        // 권한 상태 확인 및 반환
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val stateFlow = PermissionStateManager.getPermissionState(key)
                if (stateFlow == null) {
                    call.reject("No state found for key: $key")
                    return@launch
                }

                // 현재 상태 가져오기
                val state = stateFlow.first() // StateFlow의 첫 번째 값
                Log.d("PermissionStateManager", "$key state: $state")

                // 클라이언트에 응답
                val response = JSObject().put("state", state)
                withContext(Dispatchers.Main) {
                    call.resolve(response)
                }
            } catch (e: Exception) {
                Log.e("PermissionStateManager", "Error checking permission: ${e.message}")
                withContext(Dispatchers.Main) {
                    call.reject("Failed to check or update permission: ${e.message}")
                }
            }
        }
    }

    @PluginMethod
    fun connectToSamsungHealth(call: PluginCall) {
        try {
            healthMainViewModel.connectToSamsungHealth()
            call.resolve() // 성공 시 응답 전송
        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect to Samsung Health: ${e.message}")
            call.reject("Failed to connect to Samsung Health", e)
        }
    }

}