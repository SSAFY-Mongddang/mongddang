package com.mongddang.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.getcapacitor.BridgeActivity
import com.mongddang.app.data.repository.local.DataStoreRepository
import com.mongddang.app.data.repository.remote.UserRepository
import com.mongddang.app.plugin.UserInfoPlugin
import com.mongddang.app.plugin.ForegroundPlugin
import com.mongddang.app.plugin.SamsungHealthPlugin
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.mongddang.app.ui.viewmodel.HealthMainViewModel
import com.mongddang.app.ui.viewmodel.HealthViewModelFactory
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BridgeActivity(){
    private lateinit var healthMainViewModel: HealthMainViewModel
    private lateinit var healthDataStore: HealthDataStore
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        // ExamplePlugin 인스턴스 생성 후 메서드 호출
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(UserInfoPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        super.onCreate(savedInstanceState)

        healthDataStore = HealthDataService.getStore(this)


        healthMainViewModel = ViewModelProvider(
            this,
            HealthViewModelFactory(this)
        )[HealthMainViewModel::class.java]


        healthMainViewModel.exceptionResponse.observe(this) { message ->
            Log.d(TAG, "healthMainViewModel.exceptionResponse: $message")
        }




    }
}