package com.mongddang.app.healthdata

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
        val context = activity ?: run {
            call.reject("Activity context is null")
            return
        }

        // DataStore에서 모든 권한 상태 확인
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val states = context.dataStore.data
                    .map { preferences ->
                        AppConstants.PERMISSION_MAPPING.keys.associateWith { key ->
                            preferences[PermissionStateManager.getKey(key)] ?: AppConstants.WAITING
                        }
                    }
                    .first() // 모든 상태를 첫 번째 값으로 가져옴

                Log.d("PermissionStateManager", "All permission states: $states")

                // 클라이언트에 응답
                val response = JSObject()
                states.forEach { (key, state) ->
                    response.put(key, state)
                }
                withContext(Dispatchers.Main) {
                    call.resolve(response)
                }
            } catch (e: Exception) {
                Log.e("PermissionStateManager", "Error reading all permission states: ${e.message}")
                withContext(Dispatchers.Main) {
                    call.reject("Failed to retrieve all permission states: ${e.message}")
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