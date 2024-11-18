package com.mongddang.app.data.local.network

import com.mongddang.app.data.local.repository.DataStoreRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        var accessToken: String? =
            runBlocking {
                var token: String? = null
                dataStoreRepository.getAccessToken().collectLatest { accessToken ->
                    accessToken?.let {
                        // 토큰이 있는 경우
                        token = accessToken
                    } ?: run {
                        token = null
                    }
                }
                token
            }
        val request =
            requestBuilder
                .header("Authorization", "Bearer $accessToken")
                .build()
        return chain.proceed(request)
    }
}
