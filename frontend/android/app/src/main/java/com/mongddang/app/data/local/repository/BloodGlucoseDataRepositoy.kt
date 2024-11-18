package com.mongddang.app.data.local.repository

import com.mongddang.app.data.local.dao.BloodGlucoseDataDao
import com.mongddang.app.data.local.entity.BloodGlucoseData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Singleton
class BloodGlucoseDataRepository @Inject constructor(private val bloodGlucoseDataDao: BloodGlucoseDataDao) {

    // ID를 통해 혈당 데이터를 가져오는 메서드
    fun getBloodGlucoseDataById(id: Long): Flow<BloodGlucoseData> {
        return bloodGlucoseDataDao.loadDataById(id)  // 메서드 이름 일치
    }

    // 시간 간격을 통해 혈당 데이터를 가져오는 메서드
    fun getBloodGlucoseDataByTimeInterval(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<BloodGlucoseData>> {
        return bloodGlucoseDataDao.loadDataByTimeInterval(startTime, endTime)  // 메서드 이름과 파라미터 타입 일치
    }

    // 특정 데이터를 삭제하는 메서드
    suspend fun delete(bloodGlucoseData: BloodGlucoseData) {
        bloodGlucoseDataDao.delete(bloodGlucoseData)
    }
}
