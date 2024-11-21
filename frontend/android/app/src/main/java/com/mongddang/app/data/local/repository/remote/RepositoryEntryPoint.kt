package com.mongddang.app.data.local.repository.remote

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepositoryEntryPoint {
    fun provideBloodGlucoseRepository(): BloodGlucoseRepositoryImpl}