package com.mongddang.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.room.Room
import com.capacitorjs.plugins.preferences.Preferences
import com.mongddang.app.data.local.dao.BloodGlucoseDataDao
import com.mongddang.app.data.local.database.AppDatabase
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import com.mongddang.app.viewmodel.BloodGlucoseViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration() // 개발 중에는 사용할 수 있음, 실제 환경에서는 마이그레이션 구현 권장
            .build()
    }

    @Singleton
    @Provides
    fun provideBloodGlucoseDao(database: AppDatabase): BloodGlucoseDataDao {
        return database.bloodGlucoseDataDao()
    }
    
}