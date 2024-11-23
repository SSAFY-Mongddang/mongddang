package com.mongddang.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


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
}