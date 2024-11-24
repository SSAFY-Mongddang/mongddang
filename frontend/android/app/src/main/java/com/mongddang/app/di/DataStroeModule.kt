package com.mongddang.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mongddang.app.data.api.UserInfoApiService
import com.mongddang.app.data.repository.local.DataStoreRepository
import com.mongddang.app.data.repository.remote.UserRepository


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userInfoApiService: UserInfoApiService,
        dataStoreRepository: DataStoreRepository
    ): UserRepository {
        return UserRepository(userInfoApiService, dataStoreRepository)
    }

}