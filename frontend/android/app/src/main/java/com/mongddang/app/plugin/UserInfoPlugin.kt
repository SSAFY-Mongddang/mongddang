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
import com.mongddang.app.MongddangApplication
import com.mongddang.app.data.model.response.ApiResponse
import com.mongddang.app.data.model.response.UserInfoResponse
import com.mongddang.app.data.repository.remote.UserRepository
import kotlinx.coroutines.flow.first

private const val TAG = "UserInfoPlugin"

@CapacitorPlugin(name = "UserInfoPlugin")
class UserInfoPlugin: Plugin() {

    private lateinit var dataStoreRepository: DataStoreRepository

    @PluginMethod
    fun getAccessToken(call: PluginCall){
        CoroutineScope(Dispatchers.IO).launch {
            val token = call.getString("token")
            val nickname = call.getString("nickname")
            val email = call.getString("email")
            val role = call.getString("role")
            if (!token.isNullOrBlank()) {
                dataStoreRepository.saveAccessToken(token)
                val response = JSObject().apply {
                    put("message", "Token saved: $token")
                }
                dataStoreRepository.getAccessToken().collect { token ->
                    Log.d(TAG,"DataStore: Token: $token")
                }
                if(!nickname.isNullOrBlank() && !email.isNullOrBlank() && !role.isNullOrBlank()){
                    val userInfo = UserInfoResponse(
                        role = role,
                        email = email,
                        nickname = nickname
                    )
                    dataStoreRepository.validateAndSaveUserInfo(userInfo)
                    dataStoreRepository.getUserNickname().collect { nickname ->
                        Log.d(TAG,"DataStore: Nickname: $nickname")
                    }
                    dataStoreRepository.getUserEmail().collect { email ->
                        Log.d(TAG,"DataStore: Email: $email")
                    }
                    dataStoreRepository.getUserRole().collect { role ->
                        Log.d(TAG,"DataStore: Role: $role")
                    }
                    call.resolve(response)
                } else{
                    // 오류 응답 반환
                    val errorResponse = JSObject().apply {
                        put("errorCode", "INVALID_INPUT")
                        put("errorMessage", "All fields (token, nickname, email, role) are required.")
                    }
                    call.reject("User information is invalid.", errorResponse)
                }
            } else {
                val response = JSObject().apply {
                    put("errorCode", "Token is invalid.")
                }
                call.resolve(response)
            }
        }
    }

//    @PluginMethod
//    fun getUserInfo(call: PluginCall){
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val apiResponse = userRepository.getUserInfo().first() // Flow에서 첫 번째 값 수집
//                when (apiResponse) {
//                    is ApiResponse.Success -> {
//                        val userInfo = apiResponse.body
//                        val response = JSObject().apply {
//                            put("email", userInfo?.email ?: "")
//                            put("nickname", userInfo?.nickname ?: "")
//                            put("role", userInfo?.role ?: "")
//                        }
//                        call.resolve(response)
//                    }
//
//                    is ApiResponse.Error -> {
//                        val errorResponse = JSObject().apply {
//                            put("errorCode", apiResponse.errorCode)
//                            put("errorMessage", apiResponse.errorMessage)
//                        }
//                        call.resolve(errorResponse)
//                    }
//
//                    ApiResponse.Init -> {
//                        val initResponse = JSObject().apply {
//                            put("message", "Initialization state, no user info available.")
//                        }
//                        call.resolve(initResponse)
//                    }
//                }
//            } catch (e: Exception) {
//                val errorResponse = JSObject().apply {
//                    put("errorCode", "U000")
//                    put("errorMessage", "Unknown error occurred: ${e.message}")
//                }
//                call.reject("Failed to get user info", errorResponse)
//            }
//        }
//    }
}