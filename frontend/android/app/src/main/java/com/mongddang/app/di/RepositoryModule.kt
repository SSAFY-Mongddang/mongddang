package com.mongddang.app.di

import com.mongddang.app.data.local.repository.remote.BloodGlucoseRepository
import com.mongddang.app.data.local.repository.remote.BloodGlucoseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideBloodGlucoseRepository(bloodGlucoseRepositoryImpl: BloodGlucoseRepositoryImpl): BloodGlucoseRepository
}