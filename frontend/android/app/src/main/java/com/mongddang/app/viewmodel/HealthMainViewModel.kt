package com.mongddang.app.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongddang.app.utils.AppConstants
import com.mongddang.app.utils.PermissionStateManager
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.permission.Permission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private const val TAG = "HealthMainViewModel"

class HealthMainViewModel(private val healthDataStore: HealthDataStore, activity: Activity) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _exceptionResponse = MutableLiveData<String>()
    val exceptionResponse: LiveData<String> = _exceptionResponse

    /**
     * 권한 키를 Permission 객체로 매핑
     */
    fun mapToPermission(permissionKey: String): Permission {
        return AppConstants.PERMISSION_MAPPING[permissionKey.lowercase()]
            ?: throw IllegalArgumentException("Unknown permissionKey: $permissionKey")
    }

    /**
     * 권한 확인 및 요청
     */
    fun checkForPermission(
        context: Context,
        permissionKey: String
    ) {
        // PermissionKey에 따른 Permission 객체 매핑
        val permission = mapToPermission(permissionKey)
        val permissionSet = setOf(permission) // 중복 제거를 위한 변수 분리
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 현재 권한 상태 확인
                val grantedPermissions = healthDataStore.getGrantedPermissions(permissionSet)

                if (grantedPermissions.containsAll(permissionSet)) {
                    Log.d(TAG, "checkForPermission: 권한 통과!")
                } else {
                    Log.d(TAG, "checkForPermission: 권한 없음!")
                    // 상태를 NO_PERMISSION으로 업데이트
                    requestForPermission(context, permissionKey)
                }
            } catch (e: Exception) {
                Log.e(TAG, "checkForPermission failed: ${e.message}")
            }
        }
    }
    /**
     * 권한 요청
     */
    private fun requestForPermission(
        context: Context,
        permissionKey: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val permission = mapToPermission(permissionKey)
            val permissionSet = setOf(permission) // 중복 제거를 위한 변수 분리
            try {
                // 권한 요청 수행
                val result = healthDataStore.requestPermissions(permissionSet, context as Activity)
                Log.i(TAG, "requestPermissions: Success ${result.size}")
                if(result.contains(permission)){
                    PermissionStateManager.updateStateByValue(
                        context,
                        permissionKey,
                        AppConstants.SUCCESS
                    )
                } else {
                    Log.e(TAG, "Permission key not found for: $permission")
                }
            } catch (e: Exception) {
                Log.e(TAG, "requestForPermission failed: ${e.message}")
                // 모든 Permission에 대해 실패 상태 방출
            }
        }
    }

    /**
     * Samsung Health 연결
     */
//    fun connectToSamsungHealth() {
//        val permissions = AppConstants.PERMISSION_MAPPING.values.toMutableSet()
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                healthDataStore.requestPermissions(permissions, activity)
//                Log.i(TAG, "connectToSamsungHealth: Permissions requested")
//            } catch (e: Exception) {
//                Log.e(TAG, "connectToSamsungHealth failed: ${e.message}")
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel() // ViewModel이 소멸될 때 코루틴 정리
    }
}