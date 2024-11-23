package com.mongddang.app.plugin

import android.util.Log
import com.mongddang.app.data.repository.local.DataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin

private const val TAG = "UserInfoPlugin"

@CapacitorPlugin(name = "UserInfoPlugin")
class UserInfoPlugin: Plugin() {

    private lateinit var dataStoreRepository: DataStoreRepository

    @PluginMethod
    fun getAccessToken(call: PluginCall){
        CoroutineScope(Dispatchers.IO).launch {
            val token = call.getString("token")
            val nickname = call.getString("nickname")
            if (!token.isNullOrBlank() && !nickname.isNullOrBlank()) {
                dataStoreRepository.saveAccessToken(token)
                dataStoreRepository.saveUserNickname(nickname)
                val response = JSObject().apply {
                    put("message", "Token saved: $token, NickName saved: $nickname")
                }
                dataStoreRepository.getAccessToken().collect { token ->
                    Log.d(TAG,"DataStore: Token: $token")
                }
                dataStoreRepository.getUserNickname().collect {  nickname ->
                    Log.d(TAG,"DataStore: Nickname: $nickname")
                }
                call.resolve(response)
            } else {
                val response = JSObject().apply {
                    put("errorCode", "Token or NickName is invalid.")
                }
                call.resolve(response)
            }
        }

    }
}