package com.mongddang.app

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "HealthMainViewModel"

class HealthMainViewModel(private val healthDataStore: HealthDataStore, private val activity: Activity) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _permissionResponse = MutableStateFlow(Pair(AppConstants.WAITING, ""))
    private val _exceptionResponse = MutableLiveData<String>()
    val permissionResponse: StateFlow<Pair<String, String>> = _permissionResponse
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

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val grantedPermissions = healthDataStore.getGrantedPermissions(setOf(permission))
                if (grantedPermissions.containsAll(setOf(permission))) {
                    Log.d(TAG, "checkForPermission: 권한 통과!")
                    // permissionKey를 SUCCESS와 함께 방출
                    _permissionResponse.emit(Pair(AppConstants.SUCCESS, permission.dataType.name))
                } else {
                    Log.d(TAG, "checkForPermission: 권한 없음!")
                    requestForPermission(context, permission)
                }
            } catch (e: Exception) {
                Log.e(TAG, "checkForPermission failed: ${e.message}")
                _permissionResponse.emit(Pair(AppConstants.NO_PERMISSION, permission.dataType.name))
            }
        }
    }
    /**
     * 권한 요청
     */
    private fun requestForPermission(
        context: Context,
        permission: Permission
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 권한 요청 수행
                val result = healthDataStore.requestPermissions(setOf(permission), activity)
                Log.i(TAG, "requestPermissions: Success ${result.size}")
                if(result.contains(permission)){
                    _permissionResponse.emit(Pair(AppConstants.SUCCESS, permission.dataType.name))
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
    fun connectToSamsungHealth() {
        val permissions = AppConstants.PERMISSION_MAPPING.values.toMutableSet()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                healthDataStore.requestPermissions(permissions, activity)
                Log.i(TAG, "connectToSamsungHealth: Permissions requested")
            } catch (e: Exception) {
                Log.e(TAG, "connectToSamsungHealth failed: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel() // ViewModel이 소멸될 때 코루틴 정리
    }
}
