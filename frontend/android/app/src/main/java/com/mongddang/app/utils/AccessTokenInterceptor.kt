package com.mongddang.app.utils

import com.mongddang.app.data.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor
    @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        var accessToken: String? =
            runBlocking {
                var token: String? = null
                dataStoreRepository.getAccessToken().collectLatest { accessToken ->
                    accessToken?.let {
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

