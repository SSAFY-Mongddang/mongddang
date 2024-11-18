package com.mongddang.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
//    @Provides
//    @Singleton
//    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

}