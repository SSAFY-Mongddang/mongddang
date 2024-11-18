package com.mongddang.app.plugin

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl


private const val TAG = "AccessTokenPlugin"

@CapacitorPlugin(name="AccessTokenPlugin")
class AccessTokenPlugin : Plugin(){

    private lateinit var dataStoreRepositoryImpl: DataStoreRepositoryImpl

    @PluginMethod()
    suspend fun getAccessTokenPlugin(call: PluginCall){
        Log.i(TAG, "getAccessTokenPlugin: ")
        val token = call.getString("token")
        if(token != null && token != "") {
            dataStoreRepositoryImpl.saveAccessToken(token)
            val message = "토큰 : ${token}이 등록완료되었습니다."
            call.resolve(JSObject().put("message", message))
        } else{
            val errorMessage = "토큰이 유효하지 않습니다."
            call.reject("message", errorMessage)
        }
    }
}