package com.mongddang.app

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.getcapacitor.BridgeActivity
import com.mongddang.app.healthdata.viewmodel.HealthMainViewModel
import com.mongddang.app.healthdata.viewmodel.HealthViewModelFactory
import com.mongddang.app.healthdata.SamsungHealthPlugin
import com.mongddang.app.healthdata.data.local.database.AppDatabase
import com.mongddang.app.healthdata.data.local.entity.BloodGlucoseData
import com.mongddang.app.healthdata.viewmodel.BloodGlucoseViewModel
import com.mongddang.app.healthdata.viewmodel.BloodGlucoseViewModelFactory
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
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        // ExamplePlugin 인스턴스 등록
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        registerPlugin(BloodGlucosePlugin::class.java)

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

        // 테스트 데이터 생성 및 저장
        val testData = BloodGlucoseData(
            time = LocalDateTime.now(),
            glucoseValue = 5.5f,
            packageName = "com.example"
        )
        bloodGlucoseViewModel.saveBloodGlucoseData(testData)
    }
}
