package com.mongddang.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mongddang.app.healthdata.viewmodel.BloodGlucoseViewModel
import com.mongddang.app.healthdata.viewmodel.HealthViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val TAG = "BloodGlucoseActivity"

class BloodGlucoseActivity : AppCompatActivity() {

    private lateinit var bloodGlucoseViewModel: BloodGlucoseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dateTimeString = intent.getStringExtra("dateTime")
        if (!dateTimeString.isNullOrEmpty()) {
            try {
                // 전달받은 문자열을 LocalDateTime으로 변환
                val dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                Log.d(TAG, "Received dateTime: $dateTime")

                handleDateTime(dateTime) // 유효한 날짜 처리
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


}