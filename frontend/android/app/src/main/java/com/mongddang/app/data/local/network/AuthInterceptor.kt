package com.mongddang.app.data.local.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import android.util.Log

class AuthInterceptor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // DataStore에서 토큰 가져오기
        val token = runBlocking {
            dataStore.data
                .map { prefs -> prefs[DataStoreKeys.ACCESS_TOKEN_KEY] ?: "" }
                .first()
        }

        Log.d("AuthInterceptor", "Token: $token") // 토큰 로그 출력

        // 요청 빌더에 헤더 추가
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token") // Authorization 헤더 추가
            .addHeader("Content-Type", "application/json") // Content-Type 헤더 추가
//            .addHeader("Custom-Header", "CustomValue") // 사용자 정의 헤더 추가
            .build()

        return chain.proceed(newRequest) // 새로운 요청 처리
    }
}

object DataStoreKeys {
    val ACCESS_TOKEN_KEY = androidx.datastore.preferences.core.stringPreferencesKey("ACCESS_TOKEN_KEY")
}
