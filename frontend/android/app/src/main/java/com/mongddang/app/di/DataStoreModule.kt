package com.mongddang.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.capacitorjs.plugins.preferences.Preferences
import com.mongddang.app.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun providesDataStore(
        @ApplicationContext appContext: Context,
    ): DataStore<androidx.datastore.preferences.core.Preferences> =
        PreferenceDataStoreFactory.create {
            appContext.preferencesDataStoreFile(appContext.getString(R.string.app_name))
        }

}