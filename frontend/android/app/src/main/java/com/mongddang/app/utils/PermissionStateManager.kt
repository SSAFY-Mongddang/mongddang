package com.mongddang.app.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

private const val TAG = "PermissionStateManager"

class PermissionStateManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val permCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // 권한 상태 맵 (동기화 처리)
    private val _permissionStateMap: MutableMap<String, MutableStateFlow<String>> =
        Collections.synchronizedMap(mutableMapOf())
    val permissionStateMap: Map<String, StateFlow<String>> = _permissionStateMap

    // 초기화 메서드
    fun initialize(context: Context) {
        AppConstants.PERMISSION_MAPPING.keys.forEach { key ->
            _permissionStateMap[key] = MutableStateFlow(AppConstants.WAITING) // 초기화 상태 설정
            dataStore.data
                .map { preferences -> preferences[getKey(key)] ?: AppConstants.WAITING }
                .onEach { state ->
                    _permissionStateMap[key]?.value = state
                    Log.d(TAG, "Loaded state for $key: $state")
                }
                .catch { exception ->
                    Log.e(TAG, "Error loading state for $key: ${exception.message}")
                }
                .launchIn(permCoroutineScope)
        }
        logInitialStates() // 초기 상태 기록
    }

    // 특정 키의 상태 가져오기
    fun getPermissionState(key: String): String? {
        val status =  _permissionStateMap[key]?.value
        return status
    }

    // 특정 키의 상태 업데이트
    fun updatePermissionState(context: Context, key: String, state: String): Boolean {
        if (!AppConstants.PERMISSION_MAPPING.containsKey(key)) {
            Log.e(TAG, "Invalid key: $key")
            return false
        }

        if (state !in listOf(AppConstants.SUCCESS, AppConstants.WAITING)) {
            Log.e(TAG, "Invalid state: $state")
            return false
        }

        // StateFlow 업데이트
        _permissionStateMap[key]?.value = state
        Log.d(TAG, "State updated in memory: $key -> $state")

        // DataStore 업데이트
        permCoroutineScope.launch {
            try {
                dataStore.edit{ preferences ->
                    preferences[getKey(key)] = state
                }
                Log.d(TAG, "State updated in DataStore: $key -> $state")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update DataStore for $key: ${e.message}")
            }
        }
        return true
    }


    // 특정 값으로 상태 업데이트
    fun updateStateByValue(context: Context, value: String, state: String) {
        val key = AppConstants.findKeyByInsensitiveValue(value)
        if (key == null) {
            Log.e(TAG, "Invalid value: $value")
            return
        }

        val isUpdated: Boolean = updatePermissionState(context, key, state)
        if (isUpdated) {
            Log.d(TAG, "Updated state for $key to $state")
        } else {
            Log.d(TAG, "No change for $key to $state")
        }
    }

    // 초기 상태 확인용 메서드
    private fun logInitialStates() {
        permissionStateMap.forEach { (key, stateFlow) ->
            Log.d(TAG, "Initial State -> $key: ${stateFlow.value}")
        }
    }

    //data 스토어 key 가져오기
    internal fun getKey(key: String) = stringPreferencesKey(key)

    suspend fun getCurrentPermissionState(key: String, defaultState: String = AppConstants.WAITING): String {
        return try {
            dataStore.data
                .map { preferences -> preferences[getKey(key)] ?: defaultState }
                .first()
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving state for key $key: ${e.message}")
            defaultState
        }
    }

}