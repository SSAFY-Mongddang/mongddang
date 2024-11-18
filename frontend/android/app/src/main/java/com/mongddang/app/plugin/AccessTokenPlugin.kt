package com.mongddang.app.plugin

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


open class AccessTokenPlugin : Plugin() {

    protected lateinit var dataStoreRepositoryImpl: DataStoreRepositoryImpl

    @PluginMethod
    fun getAccessTokenPlugin(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            handleAccessToken(call)
        }
    }

    suspend fun handleAccessToken(call: PluginCall) {
        val token = call.getString("token")
        if (token != null && token.isNotBlank()) {
            dataStoreRepositoryImpl.saveAccessToken(token)
            val message = "토큰 : $token 이 등록완료되었습니다."
            call.resolve(JSObject().put("message", message))
        } else {
            val errorMessage = "토큰이 유효하지 않습니다."
            call.reject("message", errorMessage)
        }
    }
}