package com.mongddang.app

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.getcapacitor.BridgeActivity
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
import com.mongddang.app.viewmodel.HealthMainViewModel
import com.mongddang.app.viewmodel.HealthViewModelFactory
import com.mongddang.app.data.local.database.AppDatabase
import com.mongddang.app.data.local.entity.BloodGlucoseData
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import com.mongddang.app.data.local.repository.remote.ApiService
import com.mongddang.app.data.local.repository.remote.BloodGlucoseRepositoryImpl
import com.mongddang.app.plugin.AccessTokenPlugin
import com.mongddang.app.plugin.BloodGlucosePlugin
import com.mongddang.app.plugin.ForegroundPlugin
import com.mongddang.app.plugin.SamsungHealthPlugin
import com.mongddang.app.viewmodel.BloodGlucoseViewModel
import com.mongddang.app.viewmodel.BloodGlucoseViewModelFactory
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BridgeActivity() {

    private lateinit var healthMainViewModel: HealthMainViewModel
    private lateinit var bloodGlucoseViewModel: BloodGlucoseViewModel
    private lateinit var healthDataStore: HealthDataStore

    @Inject
    lateinit var dataStoreRepository: DataStoreRepositoryImpl

    @Inject
    lateinit var bloodGlucoseRepositoryImpl: BloodGlucoseRepositoryImpl

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        // Plugins 등록
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        registerPlugin(BloodGlucosePlugin::class.java)
        registerPlugin(AccessTokenPlugin::class.java)
        super.onCreate(savedInstanceState) // Hilt 초기화 완료
        // AccessTokenPlugin 초기화
        AccessTokenPlugin.initialize(dataStoreRepository)
        Log.d(TAG, "onCreate")

        // HealthDataStore 초기화
        healthDataStore = HealthDataService.getStore(this)

        // AppDatabase 초기화
        val appDatabase = AppDatabase.getDatabase(applicationContext)

        // ViewModel 초기화
        healthMainViewModel = ViewModelProvider(
            this,
            HealthViewModelFactory(this)
        )[HealthMainViewModel::class.java]

        healthMainViewModel.exceptionResponse.observe(this) { message ->
            Log.d(TAG, "healthMainViewModel.exceptionResponse: $message")
        }

        bloodGlucoseViewModel = ViewModelProvider(
            this,
            BloodGlucoseViewModelFactory(healthDataStore, this, appDatabase)
        )[BloodGlucoseViewModel::class.java]
    }

    fun callApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // GET 요청
                val getResponse: Response<String> = apiService.getMessage()
                if (getResponse.isSuccessful) {
                    Log.d("API GET Response", "Success: ${getResponse.body()}")
                } else {
                    Log.e("API GET Response", "Error: ${getResponse.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }

    fun sendBloodGlucoseData(bloodGlucoseRequest: BloodGlucoseRequest) {
        lifecycleScope.launch {
            try {
                // Retrofit suspend function 호출
                val response = apiService.sendBloodGlucose(bloodGlucoseRequest)

                if (response.isSuccessful) {
                    // 성공 시
                    response.body()?.let { body ->
                        Log.d("API Response", "Success: $body")
                    } ?: Log.e("API Response", "Error: Empty body")
                } else {
                    // 실패 시
                    Log.e("API Response", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // 네트워크 또는 기타 에러 처리
                Log.e("API Response", "Exception: ${e.message}")
            }
        }
    }
}
