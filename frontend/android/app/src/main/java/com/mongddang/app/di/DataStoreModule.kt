package com.mongddang.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.mongddang.app.data.local.repository.DataStoreRepository
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    companion object {
        private const val DATASTORE_FILE_NAME = "app_preferences"

        @Provides
        @Singleton
        fun providesDataStore(
            @ApplicationContext appContext: Context,
        ): DataStore<Preferences> {
            val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            return PreferenceDataStoreFactory.create(
                scope = applicationScope,
                produceFile = { appContext.preferencesDataStoreFile(DATASTORE_FILE_NAME) }
            )
        }
    }

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}
