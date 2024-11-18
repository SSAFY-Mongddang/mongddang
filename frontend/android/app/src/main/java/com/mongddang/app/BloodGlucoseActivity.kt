package com.mongddang.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mongddang.app.data.local.database.AppDatabase
import com.mongddang.app.utils.showToast
import com.mongddang.app.viewmodel.BloodGlucoseViewModel
import com.mongddang.app.viewmodel.BloodGlucoseViewModelFactory
import com.mongddang.app.viewmodel.HealthMainViewModel
import com.mongddang.app.viewmodel.HealthViewModelFactory
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.HealthDataStore
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

private const val TAG = "BloodGlucoseActivity"


class BloodGlucoseActivity : AppCompatActivity() {

    private lateinit var bloodGlucoseViewModel: BloodGlucoseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthDataStore = HealthDataService.getStore(this)
        val appDatabase = AppDatabase.getDatabase(this)

        // ViewModelFactory를 생성하고 ViewModel 생성
        val factory = BloodGlucoseViewModelFactory(healthDataStore, this, appDatabase)
        bloodGlucoseViewModel = ViewModelProvider(this, factory).get(BloodGlucoseViewModel::class.java)

        val dateTimeString = intent.getStringExtra("dateTime")
        if (!dateTimeString.isNullOrEmpty()) {
            try {
                // 전달받은 문자열을 LocalDateTime으로 변환
                val dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                Log.d(TAG, "Received dateTime: $dateTime")

                handleDateTime(dateTime) // 유효한 날짜 처리
                setGlucoseDataObservers()
            } catch (e: DateTimeParseException) {
                Log.e(TAG, "잘못된 날짜 형식으로 전달되었습니다: $dateTimeString", e)
            }
        } else {
            Log.e(TAG, "dateTime 값이 전달되지 않았습니다.")
        }
    }

    private fun handleDateTime(dateTime: LocalDateTime) {
        // ViewModel을 통해 데이터 처리
        bloodGlucoseViewModel.readBloodGlucoseData(dateTime)
        Log.d(TAG, "처리된 날짜 값: $dateTime")
    }

    private fun setGlucoseDataObservers() {
        Log.i(TAG, "BloodGlucoseActivity setGlucoseDataObservers" )
        /** Update glucose UI */
        bloodGlucoseViewModel.glucoseData.observe(this) { glucoseLevels ->
            // 예: glucoseLevels 리스트를 어댑터로 업데이트하거나 화면에 표시
            glucoseLevels.forEach { data ->
                bloodGlucoseViewModel.saveBloodGlucoseData(data.toEntity())
            }
        }

        /** Show toast on exception occurrence **/
        bloodGlucoseViewModel.exceptionResponse.observe(this) { message ->
            showToast(this, message)
        }
    }




}