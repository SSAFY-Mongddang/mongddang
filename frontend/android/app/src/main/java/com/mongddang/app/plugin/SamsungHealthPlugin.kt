package com.mongddang.app.plugin

import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.ui.viewmodel.HealthMainViewModel
import com.mongddang.app.ui.viewmodel.HealthViewModelFactory
import com.mongddang.app.utils.PermissionStateManager
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "SamsungHealthPlugin"

// Extension to get DataStore from Context
private val android.content.Context.dataStore by preferencesDataStore(name = "user_preferences")

@CapacitorPlugin(name="SamsungHealthPlugin")
class SamsungHealthPlugin: Plugin(){
    private lateinit var healthMainViewModel: HealthMainViewModel
    private lateinit var permissionStateManager: PermissionStateManager

    override fun load() {
        super.load()
        // HealthMainViewModel 초기화
        healthMainViewModel = HealthViewModelFactory(context).create(HealthMainViewModel::class.java)

        // PermissionStateManager 수동 초기화
        val context = activity?.applicationContext
            ?: throw IllegalStateException("Activity context is null")
        permissionStateManager = PermissionStateManager(context.dataStore)
    }

    @PluginMethod
    suspend fun checkHealthPermission(call: PluginCall) {
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
        val checkPermission = healthMainViewModel.checkPermission(healthDataType)
        Log.d(TAG, "requestHealthPermission: $checkPermission")
        val response = JSObject().apply {
            put("granted", checkPermission)
        }
        call.resolve(response)
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
        val checkPermission = healthMainViewModel.requestPermission(context, healthDataType)
        Log.d(TAG, "requestHealthPermission: $checkPermission")
    }

}