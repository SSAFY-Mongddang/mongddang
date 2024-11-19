package com.mongddang.app.plugin

import android.content.Intent
import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.BloodGlucoseActivity
import com.mongddang.app.utils.AppConstants
import com.mongddang.app.utils.PermissionStateManager
import com.mongddang.app.utils.dataStore
import com.mongddang.app.viewmodel.HealthMainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val TAG = "BloodGlucosePlugin"

@CapacitorPlugin(name="BloodGlucosePlugin")
class BloodGlucosePlugin : Plugin(){

    private lateinit var healthMainViewModel: HealthMainViewModel

    @PluginMethod
    fun getThisTimeBloodGlucose(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            // 혈당 권한 확인
            val isBloodGlucoseAllowed = checkBloodGlucosePermission()
            if (!isBloodGlucoseAllowed) {
                val errorMessage = "혈당 권한이 없습니다."
                Log.e(TAG, errorMessage)
                call.reject(errorMessage)
                return@launch
            }

            // 요청된 날짜 문자열 가져오기
            val requestDateTimeString = call.getString("datetime")
            if (requestDateTimeString == null) {
                val errorMessage = "datetime 값이 누락되었습니다."
                Log.e(TAG, errorMessage)
                call.reject(errorMessage)
                return@launch
            }

            // LocalDateTime 변환
            val requestLocalDateTime = try {
                LocalDateTime.parse(requestDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            } catch (e: DateTimeParseException) {
                val errorMessage = "잘못된 날짜 형식입니다. ISO_LOCAL_DATE_TIME 형식이어야 합니다: $requestDateTimeString"
                Log.e(TAG, errorMessage, e)
                call.reject(errorMessage)
                return@launch
            }

            // 최소 및 현재 날짜 설정
            val minimumDate = AppConstants.minimumDate
            val currentDate = AppConstants.currentDate

            // 날짜 유효성 검사
            if (requestLocalDateTime.isBefore(minimumDate) || requestLocalDateTime.isAfter(currentDate)) {
                val errorMessage = "유효하지 않은 날짜입니다: $requestLocalDateTime"
                Log.e(TAG, errorMessage)
                call.reject(errorMessage)
                return@launch
            }

            // 유효한 날짜일 경우 처리
            val response = JSObject().apply {
                put("datetime", requestLocalDateTime.toString())
                put("status", "valid")
            }
            Log.d(TAG, "getThisTimeBloodGlucose: $requestLocalDateTime 는 유효한 날짜입니다.")

            // Intent를 통한 새로운 Activity 시작
            val context = getActivity()
            if (context == null) {
                val errorMessage = "Context를 가져올 수 없습니다."
                Log.e(TAG, errorMessage)
                call.reject(errorMessage)
                return@launch
            }

            try {
                val intent = Intent(context, BloodGlucoseActivity::class.java).apply {
                    putExtra("dateTime", requestLocalDateTime.toString()) // 전달할 dateTime 값
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                val errorMessage = "Failed to start activity: ${e.message}"
                Log.e(TAG, errorMessage, e)
                call.reject(errorMessage)
                return@launch
            }

            // 성공적으로 날짜가 유효하고 처리 완료된 경우 응답
            call.resolve(response)
        }
    }

    @PluginMethod
    fun checkUserBloodGlucosePerm(call: PluginCall){
        CoroutineScope(Dispatchers.IO).launch {
            val isGranted = checkBloodGlucosePermission()
            if(isGranted){
                call.resolve(JSObject().put("isGranted", true))
            }
            call.resolve(JSObject().put("isGranted", false))
        }
    }

    suspend fun checkBloodGlucosePermission(): Boolean {
        return try {
            val states = context.dataStore.data
                .map { preferences ->
                    AppConstants.PERMISSION_MAPPING.keys.associateWith { key ->
                        preferences[PermissionStateManager.getKey(key)] ?: AppConstants.WAITING
                    }
                }
                .first() // 모든 상태를 첫 번째 값으로 가져옴
            states[AppConstants.BLOOD_GLUCOSE] == AppConstants.SUCCESS
        } catch (e: Exception) {
            Log.e(TAG, "checkBloodGlucosePermission 혈당 권한 상태는: ${e.message}")
            false
        }
    }

}