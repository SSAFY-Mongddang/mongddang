package com.mongddang.app.plugin

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AccessTokenPlugin"
private val Context.dataStore by preferencesDataStore(name = "app_preferences")


@CapacitorPlugin(name = "AccessTokenPlugin")
class AccessTokenPlugin : Plugin() {

    companion object {
        private var _dataStoreRepository: DataStoreRepositoryImpl? = null

        fun initialize(dataStoreRepository: DataStoreRepositoryImpl) {
            _dataStoreRepository = dataStoreRepository
        }

        val dataStoreRepository: DataStoreRepositoryImpl
            get() = _dataStoreRepository ?: throw IllegalStateException("DataStoreRepository is not initialized")
    }


    @PluginMethod
    fun getAccessTokenPlugin(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            val token = call.getString("token")
            val nickName = call.getString("nickName")
            if (!token.isNullOrBlank() && !nickName.isNullOrBlank()) {
                dataStoreRepository.saveAccessToken(token)
                dataStoreRepository.saveUserNickName(nickName)

                val response = JSObject().apply {
                    put("message", "Token saved: $token, NickName saved: $nickName")
                }
                call.resolve(response)
            } else {
                call.reject("Token or NickName is invalid.")
            }
        }
    }
}