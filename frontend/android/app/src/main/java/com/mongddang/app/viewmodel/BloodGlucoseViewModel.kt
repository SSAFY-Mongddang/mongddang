package com.mongddang.app.viewmodel

import android.app.Activity
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mongddang.app.data.local.entity.BloodGlucoseData
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.data.HealthDataPoint
import com.samsung.android.sdk.health.data.data.entries.BloodGlucose
import com.samsung.android.sdk.health.data.request.DataType
import java.time.LocalDateTime
import androidx.lifecycle.viewModelScope
import com.mongddang.app.data.local.dao.BloodGlucoseDataDao
import com.mongddang.app.data.local.database.AppDatabase
import com.mongddang.app.data.local.repository.BloodGlucoseDataRepository
import com.mongddang.app.utils.AppConstants
import com.mongddang.app.utils.dateFormat
import com.mongddang.app.utils.getExceptionHandler
import com.samsung.android.sdk.health.data.request.DataTypes
import com.samsung.android.sdk.health.data.request.LocalTimeFilter
import com.samsung.android.sdk.health.data.request.Ordering
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "BloodGlucoseViewModel"

class BloodGlucoseViewModel (
    private val healthDataStore: HealthDataStore,
    activity: Activity,
    private val appDatabase: AppDatabase
    ) :
    ViewModel() {
    data class GlucoseLevel(
        val time: LocalDateTime,
        val glucoseValue: Float,
        val packageName: String
    ){
        fun toEntity(): BloodGlucoseData {
            return BloodGlucoseData(
                time = time,
                glucoseValueMgPerDl = (this.glucoseValue * 18).toInt(),
                packageName = "com.samsung.health"
            )
        }
    }

    private val _exceptionResponse: MutableLiveData<String> = MutableLiveData<String>()
    private val exceptionHandler = getExceptionHandler(activity, _exceptionResponse)
    private val _dailyGlucoseLevel = MutableLiveData<List<BloodGlucose>>()
    val dailyGlucoseLevel: LiveData<List<BloodGlucose>> = _dailyGlucoseLevel
    val dayStartTimeAsText = ObservableField<String>()
    val exceptionResponse: LiveData<String> = _exceptionResponse
    private val _glucoseData : MutableLiveData<List<GlucoseLevel>> =  MutableLiveData()
    val glucoseData: LiveData<List<GlucoseLevel>> = _glucoseData

    fun processGlucoseData(bloodGlucoseDataList: List<HealthDataPoint>) {
        Log.i(TAG, "Processing Glucose Data")
        val glucoseDataList = mutableListOf<GlucoseLevel>()
        bloodGlucoseDataList.forEach { glucoseData ->
            val time = glucoseData.getStartLocalDateTime()
            val glucoseValue = glucoseData.getValue(DataType.BloodGlucoseType.GLUCOSE_LEVEL)?.toFloat() ?: 0.0f
            val packageName = "com.samsung.health"

            if (glucoseValue != 0.0f) {
                val glucoseLevel = GlucoseLevel(time, glucoseValue, packageName)
                glucoseDataList.add(glucoseLevel)

                // GlucoseLevel을 BloodGlucoseData로 변환하여 저장
                val saveglu = saveBloodGlucoseData(glucoseLevel.toEntity())
                Log.d(TAG, "saveglu $saveglu")
            }
        }
        _glucoseData.postValue(glucoseDataList)
    }

    fun saveBloodGlucoseData(data: BloodGlucoseData) {
        Log.d(TAG, "saveBloodGlucoseData: ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 데이터 중복 확인
                val existingData = appDatabase.bloodGlucoseDataDao().exists(data.time, data.glucoseValueMgPerDl)
                if (existingData == null) {
                    // 중복되지 않은 경우 삽입
                    val newRowId = appDatabase.bloodGlucoseDataDao().insert(data)
                    if (newRowId != -1L) {
                        val insertedData = appDatabase.bloodGlucoseDataDao().fetchById(newRowId)
                        Log.d(TAG, "Inserted data: $insertedData")
                    } else {
                        Log.e(TAG, "Failed to insert data: $data")
                    }
                } else {
                    Log.d(TAG, "Data already exists: $existingData")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting data: ${e.message}", e)
            }
        }
    }
    fun readBloodGlucoseData(dateTime: LocalDateTime) {
        Log.i(TAG, "readBloodGlucoseData")
        dayStartTimeAsText.set(dateTime.format(dateFormat))

        val localTimeFilter = LocalTimeFilter.of(dateTime, dateTime.plusDays(1))
        val readRequest = DataTypes.BLOOD_GLUCOSE.readDataRequestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .setOrdering(Ordering.DESC)
            .build()

        viewModelScope.launch(AppConstants.SCOPE_IO_DISPATCHERS + exceptionHandler) {
            val glucoseLevelList = healthDataStore.readData(readRequest).dataList
            Log.d(TAG, "readBloodGlucoseData glucoseLevelList:" )
            processGlucoseData(glucoseLevelList)
        }
    }
//
//    fun getHourlyBloodGlucoseData(dateTime: LocalDateTime): LiveData<List<BloodGlucoseData>>  {
//        Log.d(TAG, "getHourlyBloodGlucoseData: ")
//        val startTime = dateTime
//        val endTime =dateTime.plusMinutes(60)
//        return getHourlyBloodGlucoseData()
//    }

}