package com.mongddang.app.data.local.network

import com.mongddang.app.data.local.repository.DataStoreRepository
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreRepository.getAccessToken().firstOrNull() ?: ""
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
