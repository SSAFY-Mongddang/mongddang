package com.mongddang.app

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "PermissionStateManager"

object PermissionStateManager {
    // 권한 상태 맵
    val permissionStateMap: MutableMap<String, MutableStateFlow<String>> = mutableMapOf()

    init {
        // AppConstants.PERMISSION_MAPPING의 키로 초기화하며, 초기 상태를 WAITING으로 설정
        AppConstants.PERMISSION_MAPPING.keys.forEach { key ->
            permissionStateMap[key] = MutableStateFlow(AppConstants.WAITING)
        }
    }

    // 특정 키의 상태 가져오기
    fun getPermissionState(key: String): StateFlow<String>? {
        return permissionStateMap[key]
    }

    // 특정 키의 상태 업데이트
    fun updatePermissionState(key: String, state: String) {
        if (!AppConstants.PERMISSION_MAPPING.containsKey(key)) {
            Log.e("PermissionStateManager", "Invalid key: $key")
            return
        }

        if (state !in listOf(AppConstants.SUCCESS, AppConstants.WAITING)) {
            Log.e("PermissionStateManager", "Invalid state: $state")
            return
        }

        permissionStateMap[key]?.value = state
        Log.d("PermissionStateManager", "State updated: $key -> $state")
    }

    fun updateStateByValue(value: String, state: String) {
        // AppConstants.PERMISSION_MAPPING에서 value에 해당하는 key를 찾음
        val key = AppConstants.findKeyByInsensitiveValue(value)

        if (key == null) {
            Log.e(TAG ,"Invalid value: $value")
            return
        }

        // 상태 업데이트
        PermissionStateManager.updatePermissionState(key, state)
        Log.d("PermissionStateManager", "Updated state for $key to $state")
    }

    // 초기 상태 확인용 메서드 (디버깅)
    fun logInitialStates() {
        permissionStateMap.forEach { (key, stateFlow) ->
            Log.d("PermissionStateManager", "Initial State -> $key: ${stateFlow.value}")
        }
    }
}