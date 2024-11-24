package com.mongddang.app.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class HealthMainViewModel(
    private val healthDataStore: HealthDataStore, activity: Activity
) : ViewModel() {

    private val _exceptionResponse = MutableLiveData<String>()
    val exceptionResponse: LiveData<String> = _exceptionResponse

    private lateinit var permissionStateManager: PermissionStateManager

    /**
     * 권한 키를 Permission 객체로 매핑
     */
    fun mapToPermission(permissionKey: String): Permission {
        return AppConstants.PERMISSION_MAPPING[permissionKey.lowercase()]
            ?: throw IllegalArgumentException("Unknown permissionKey: $permissionKey")
    }

    /**
     * 권한 확인 후 요청
     */
    suspend fun ensurePermission(context: Context, permissionKey: String) {
        if (checkPermission(permissionKey)) {
            Log.d(TAG, "ensurePermission: Permission already granted for $permissionKey")
            permissionStateManager.updateStateByValue(context, permissionKey, AppConstants.SUCCESS)
        } else {
            Log.d(TAG, "ensurePermission: Permission not granted, requesting $permissionKey")
            requestPermission(context, permissionKey)
        }
    }

    /**
     * 권한 확인
     */
    suspend fun checkPermission(permissionKey: String): Boolean {
        val permission = mapToPermission(permissionKey)
        val permissionSet = setOf(permission)

        return try {
            val grantedPermissions = healthDataStore.getGrantedPermissions(permissionSet)
            grantedPermissions.containsAll(permissionSet)
        } catch (e: Exception) {
            Log.e(TAG, "checkPermission failed: ${e.message}")
            false
        }
    }

    /**
     * 권한 요청
     */
    fun requestPermission(context: Context, permissionKey: String) {
        val activity = context as? Activity ?: throw IllegalArgumentException("Context must be an Activity.")
        val permission = mapToPermission(permissionKey)
        val permissionSet = setOf(permission)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = healthDataStore.requestPermissions(permissionSet, activity)
                if (result.contains(permission)) {
                    permissionStateManager.updateStateByValue(context, permissionKey, AppConstants.SUCCESS)
                    Log.i(TAG, "Permission granted for $permissionKey")
                } else {
                    Log.e(TAG, "Permission not granted for $permissionKey")
                }
            } catch (e: Exception) {
                Log.e(TAG, "requestPermission failed: ${e.message}")
                _exceptionResponse.postValue("Permission request failed: ${e.message}")
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel() // ViewModel이 소멸될 때 코루틴 정리
    }
}