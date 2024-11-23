package com.mongddang.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope
//애플리케이션 전역에서 사용할 수 있는 CoroutineScope를 제공
//주기적 데이터 동기화를 위해 사용
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @ApplicationScope
    fun providesApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
}