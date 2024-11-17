package com.mongddang.app

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin

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

    private fun requestPermissionForHealthData(call: PluginCall) {
        val permissionKey = call.getString("permissionKey")?: run {
            call.reject("dataTypes parameter is required")
            return
        }

        val context = activity ?: run {
            call.reject("Activity context is null")
            return
        }

        val currentPermission = healthMainViewModel.mapToPermission(permissionKey)
        Log.d(TAG, "requestPermissionForHealthData: $currentPermission")

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