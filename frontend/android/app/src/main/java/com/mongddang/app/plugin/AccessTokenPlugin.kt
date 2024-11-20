package com.mongddang.app.plugin

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AccessTokenPlugin"

@CapacitorPlugin(name = "AccessTokenPlugin")
class AccessTokenPlugin : Plugin() {

    // DataStore 및 DataStoreRepositoryImpl 초기화
    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("app_preferences")
        }
    }

    // DataStoreRepositoryImpl 객체를 직접 생성
    private val dataStoreRepositoryImpl: DataStoreRepositoryImpl by lazy {
        DataStoreRepositoryImpl(dataStore)
    }

    override fun load() {
        super.load()
        Log.d(TAG, "AccessTokenPlugin loaded without Hilt")
    }

    @PluginMethod
    fun getAccessTokenPlugin(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            handleAccessToken(call)
        }
    }

    private suspend fun handleAccessToken(call: PluginCall) {
        val token = call.getString("token")
        val nickName = call.getString("nickName")
        if (!token.isNullOrBlank() && !nickName.isNullOrEmpty()) {
            dataStoreRepositoryImpl.saveAccessToken(token)
            dataStoreRepositoryImpl.saveUserNickName(nickName)
            val response = JSObject().apply {
                put("message", "Token : $token, nickName: $nickName")
            }
            call.resolve(response)
            Log.d(TAG, "handleAccessToken:  $token $nickName")
        } else {
            call.reject("Token is invalid.")
            Log.d(TAG, "handleAccessToken:  fail")
        }
    }
}
