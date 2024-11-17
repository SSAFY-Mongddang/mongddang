package com.mongddang.app.healthdata.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mongddang.app.healthdata.data.local.converters.Converters
import com.mongddang.app.healthdata.data.local.dao.BloodGlucoseDataDao
import com.mongddang.app.healthdata.data.local.entity.BloodGlucoseData

@Database(entities = [BloodGlucoseData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bloodGlucoseDataDao(): BloodGlucoseDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}