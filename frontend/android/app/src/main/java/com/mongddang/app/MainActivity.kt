package com.mongddang.app

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.getcapacitor.BridgeActivity
import com.mongddang.app.viewmodel.HealthMainViewModel
import com.mongddang.app.viewmodel.HealthViewModelFactory
import com.mongddang.app.data.local.database.AppDatabase
import com.mongddang.app.data.local.entity.BloodGlucoseData
import com.mongddang.app.plugin.AccessTokenPlugin
import com.mongddang.app.plugin.BloodGlucosePlugin
import com.mongddang.app.plugin.ForegroundPlugin
import com.mongddang.app.plugin.SamsungHealthPlugin
import com.mongddang.app.viewmodel.BloodGlucoseViewModel
import com.mongddang.app.viewmodel.BloodGlucoseViewModelFactory
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BridgeActivity() {

    private lateinit var healthMainViewModel: HealthMainViewModel
    private lateinit var bloodGlucoseViewModel: BloodGlucoseViewModel
    private lateinit var healthDataStore: HealthDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        // ExamplePlugin 인스턴스 등록
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        registerPlugin(BloodGlucosePlugin::class.java)
        registerPlugin(AccessTokenPlugin::class.java)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        // HealthDataStore 초기화
        healthDataStore = HealthDataService.getStore(this)

        // AppDatabase 초기화
        val appDatabase = AppDatabase.getDatabase(applicationContext)

        // HealthMainViewModel 초기화
        healthMainViewModel = ViewModelProvider(
            this,
            HealthViewModelFactory(this)
        )[HealthMainViewModel::class.java]

        // 예외 발생 시 토스트 출력
        healthMainViewModel.exceptionResponse.observe(this) { message ->
            Log.d(TAG, "healthMainViewModel.exceptionResponse: $message")
        }

        // BloodGlucoseViewModel 초기화
        bloodGlucoseViewModel = ViewModelProvider(
            this,
            BloodGlucoseViewModelFactory(healthDataStore, this, appDatabase)
        )[BloodGlucoseViewModel::class.java]

    }
}
