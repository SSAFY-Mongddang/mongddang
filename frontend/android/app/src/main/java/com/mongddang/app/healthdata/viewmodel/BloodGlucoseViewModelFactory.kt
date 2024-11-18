package com.mongddang.app.healthdata.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mongddang.app.healthdata.data.local.database.AppDatabase
import com.samsung.android.sdk.health.data.HealthDataStore

class BloodGlucoseViewModelFactory (
    private val healthDataStore: HealthDataStore,
    private val activity: Activity,
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BloodGlucoseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BloodGlucoseViewModel(healthDataStore, activity, appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}