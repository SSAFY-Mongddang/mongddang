package com.mongddang.app.plugin

import android.content.Intent
import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.BloodGlucoseActivity
import com.mongddang.app.MainActivity
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
import com.mongddang.app.data.local.repository.remote.BloodGlucoseRepository
import com.mongddang.app.data.local.repository.remote.BloodGlucoseRepositoryImpl
import com.mongddang.app.data.local.repository.remote.RepositoryEntryPoint
import com.mongddang.app.utils.AppConstants
import com.mongddang.app.utils.PermissionStateManager
import com.mongddang.app.utils.dataStore
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val TAG = "BloodGlucosePlugin"

private val pluginScope = CoroutineScope(Dispatchers.IO)

@CapacitorPlugin(name = "BloodGlucosePlugin")
class BloodGlucosePlugin : Plugin() {

    private val bloodGlucoseRepository: BloodGlucoseRepositoryImpl?
        get() {
            val activity = getActivity()
            return if (activity is MainActivity) {
                activity.bloodGlucoseRepositoryImpl
            } else {
                null
            }
        }

//    private var mainActivity: MainActivity? = null
      private lateinit var mainActivity: MainActivity

//    fun initialize(activity: MainActivity) {
//        this.mainActivity = activity
//    }




    @PluginMethod
    fun getThisTimeBloodGlucose(call: PluginCall) {
        pluginScope.launch {
            try {
                // 권한 확인
                if (!checkBloodGlucosePermission()) {
                    call.reject("Blood glucose permission is not granted.")
                    return@launch
                }

                // 요청 날짜 검증
                val requestDateTime = validateRequestDate(call) ?: return@launch

                // Activity Context 확인
                val context = getActivity()
                if (context == null) {
                    call.reject("Activity context is null.")
                    return@launch
                }

                // Intent 생성 및 시작
                val intent = Intent(context, BloodGlucoseActivity::class.java).apply {
                    putExtra("dateTime", requestDateTime.toString())
                }
                context.startActivity(intent)

                // 성공 응답
                val response = JSObject().apply {
                    put("datetime", requestDateTime.toString())
                    put("status", "valid")
                }
                call.resolve(response)
                Log.d(TAG, "getThisTimeBloodGlucose: Valid date - $requestDateTime")
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error in getThisTimeBloodGlucose: ${e.message}", e)
                call.reject("An unexpected error occurred: ${e.message}")
            }
        }
    }

    // 날짜 검증 로직 분리
    private fun validateRequestDate(call: PluginCall): LocalDateTime? {
        val requestDateTimeString = call.getString("datetime")
        if (requestDateTimeString == null) {
            val errorMessage = "Missing 'datetime' parameter."
            Log.e(TAG, errorMessage)
            call.reject(errorMessage)
            return null
        }

        val requestLocalDateTime = try {
            LocalDateTime.parse(requestDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            val errorMessage = "Invalid date format. Use ISO_LOCAL_DATE_TIME: $requestDateTimeString"
            Log.e(TAG, errorMessage, e)
            call.reject(errorMessage)
            return null
        }

        val minimumDate = AppConstants.minimumDate
        val currentDate = AppConstants.currentDate
        if (requestLocalDateTime.isBefore(minimumDate) || requestLocalDateTime.isAfter(currentDate)) {
            val errorMessage = "Invalid date range: $requestLocalDateTime"
            Log.e(TAG, errorMessage)
            call.reject(errorMessage)
            return null
        }

        return requestLocalDateTime
    }

    @PluginMethod
    fun checkUserBloodGlucosePerm(call: PluginCall) {
        pluginScope.launch {
            try {
                val isGranted = checkBloodGlucosePermission()
                call.resolve(JSObject().put("isGranted", isGranted))
            } catch (e: Exception) {
                val errorMessage = "Failed to check blood glucose permission: ${e.message}"
                Log.e(TAG, errorMessage, e)
                call.reject(errorMessage)
            }
        }
    }

        private fun ensureRepositoryInitialized() {
        if (!::mainActivity.isInitialized) {
            try {
                Log.d(TAG, "BloodGlucoseRepository initialized successfully.")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize BloodGlucoseRepository: ${e.message}", e)
            }
        }
    }


//    private fun ensureRepositoryInitialized() {
//        if (!::bloodGlucoseRepositoryImpl.isInitialized) {
//            try {
//                val entryPoint = EntryPointAccessors.fromApplication(
//                    context,
//                    RepositoryEntryPoint::class.java
//                )
//                bloodGlucoseRepositoryImpl = entryPoint.provideBloodGlucoseRepository()
//                Log.d(TAG, "BloodGlucoseRepository initialized successfully.")
//            } catch (e: Exception) {
//                Log.e(TAG, "Failed to initialize BloodGlucoseRepository: ${e.message}", e)
//            }
//        }
//    }
    @PluginMethod
    fun getTestApi(call: PluginCall) {
        pluginScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val activity = getActivity()
                if (activity is MainActivity) {
                    // MainActivity에 접근하여 필요한 작업 수행
                    activity.callApi()
                } else {
                    call.reject("MainActivity is not available.")
                }
            }
        }
    }

    private suspend fun checkBloodGlucosePermission(): Boolean {
        return try {
            val states = context.dataStore.data
                .map { preferences ->
                    AppConstants.PERMISSION_MAPPING.keys.associateWith { key ->
                        preferences[PermissionStateManager.getKey(key)] ?: AppConstants.WAITING
                    }
                }
                .first()

            states[AppConstants.BLOOD_GLUCOSE] == AppConstants.SUCCESS
        } catch (e: Exception) {
            Log.e(TAG, "Error checking blood glucose permission: ${e.message}", e)
            false
        }
    }

    @PluginMethod
        fun sendBloodGlucoseApi(call: PluginCall) {
        pluginScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val activity = getActivity()
                if (activity is MainActivity) {
                    // MainActivity에 접근하여 필요한 작업 수행
                    val bloodGlucoseRequest = BloodGlucoseRequest(
                        bloodSugarLevel = 120,
                        measurementTime = LocalDateTime.now(),
                        packageName = "com.samsung.app"

                    )
                    activity.sendBloodGlucoseData(bloodGlucoseRequest)
                } else {
                    call.reject("MainActivity is not available.")
                }
            }
        }
    }
}
