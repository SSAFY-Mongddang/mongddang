package com.mongddang.app.healthdata.viewmodel

import android.app.Activity
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongddang.app.healthdata.data.local.entity.BloodGlucoseData
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.data.HealthDataPoint
import com.samsung.android.sdk.health.data.data.entries.BloodGlucose
import com.samsung.android.sdk.health.data.request.DataType
import java.time.LocalDateTime
import androidx.lifecycle.viewModelScope
import com.mongddang.app.healthdata.utils.AppConstants
import com.mongddang.app.healthdata.utils.dateFormat
import com.mongddang.app.healthdata.utils.getExceptionHandler
import com.samsung.android.sdk.health.data.request.DataTypes
import com.samsung.android.sdk.health.data.request.LocalTimeFilter
import com.samsung.android.sdk.health.data.request.Ordering
import kotlinx.coroutines.launch


private const val TAG = "BloodGlucoseViewModel"

class BloodGlucoseViewModel(
    private val healthDataStore: HealthDataStore,
    activity: Activity,
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
                glucoseValue = this.glucoseValue,
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
            val packageName = glucoseData.dataSource?.appId ?: "unknown"

            if (glucoseValue != 0.0f) {
                val glucoseLevel = GlucoseLevel(time, glucoseValue, packageName)
                glucoseDataList.add(glucoseLevel)

                // GlucoseLevel을 BloodGlucoseData로 변환하여 저장
//                val saveglu = saveBloodGlucoseData(glucoseLevel.toEntity())
//                Log.d(TAG, "saveglu $saveglu")
            }
        }
        _glucoseData.postValue(glucoseDataList)
    }

//    // ViewModel 내에서
//    fun saveBloodGlucoseData(data: BloodGlucoseData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val newRowId = appDatabase.bloodGlucoseDataDao().insertBloodGlucoseData(data)
//            if (newRowId != -1L) {
//                val insertedData = appDatabase.bloodGlucoseDataDao().getBloodGlucoseDataById(newRowId)
//                Log.d(TAG, "Inserted data: $insertedData")
//            }
//        }
//    }
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
            glucoseLevelList.forEach{data ->
                Log.d(TAG, "data.dataSource : ${data.dataSource}" )
                Log.d(TAG, "data.dataSource?.appId : ${data.dataSource?.appId}" )
                Log.d(TAG, "data.dataSource?.deviceId : ${data.dataSource?.deviceId}" )
                Log.d(TAG, "data.uid : ${data.uid}" )
                Log.d(TAG, "data.getStartLocalDateTime() : ${data.getStartLocalDateTime()}" )
                Log.d(TAG, "data.getEndLocalDateTime() : ${data.getEndLocalDateTime()}" )
                Log.d(TAG, "data.dataSource : ${DataType.UserProfileDataType}")
            }
//            processGlucoseData(glucoseLevelList)
        }
    }



}