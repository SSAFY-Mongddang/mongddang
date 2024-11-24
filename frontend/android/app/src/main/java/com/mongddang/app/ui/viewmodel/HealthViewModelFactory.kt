package com.mongddang.app.ui.viewmodel

import android.app.Activity
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore

class HealthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        HealthMainViewModel::class.java ->
            HealthMainViewModel(HealthDataService.getStore(context), context as Activity)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}

