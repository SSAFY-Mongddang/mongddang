package com.mongddang.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mongddang.app.data.api.UserInfoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.mongddang.app.BuildConfig
import com.mongddang.app.utils.AccessTokenInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json =
            Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        return Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(accessTokenInterceptor: AccessTokenInterceptor) =
        OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            addNetworkInterceptor(accessTokenInterceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
            build()
        }

//
//    @Singleton
//    @Provides
//    fun providesGlucoseApiService(retrofit: Retrofit): BloodGlucoseApiService =
//        retrofit.create(BloodGlucoseApiService::class.java)

    @Singleton
    @Provides
    fun privoidesUserInfoApiService(retrofit: Retrofit): UserInfoApiService =
        retrofit.create(UserInfoApiService::class.java)
}