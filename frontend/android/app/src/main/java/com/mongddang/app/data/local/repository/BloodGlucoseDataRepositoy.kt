package com.mongddang.app.data.local.repository

import com.mongddang.app.data.local.dao.BloodGlucoseDataDao
import com.mongddang.app.data.local.entity.BloodGlucoseData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Singleton
class BloodGlucoseDataRepository @Inject constructor(
    private val bloodGlucoseDataDao: BloodGlucoseDataDao
) {

    // 특정 ID로 데이터를 Flow로 가져오기
    fun fetchByIdFlow(id: Long): Flow<BloodGlucoseData?> =
        bloodGlucoseDataDao.fetchByIdFlow(id)

    // 특정 ID로 데이터를 가져오기 (suspend)
    suspend fun fetchById(id: Long): BloodGlucoseData? =
        bloodGlucoseDataDao.fetchById(id)

    // 특정 시간 간격 내 데이터를 Flow로 가져오기
    fun fetchByTimeInterval(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<BloodGlucoseData>> =
        bloodGlucoseDataDao.fetchByTimeInterval(startTime, endTime)

    // 데이터 삽입 및 업데이트
    suspend fun save(bloodGlucoseData: BloodGlucoseData): Long =
        bloodGlucoseDataDao.insert(bloodGlucoseData)

    // 특정 데이터를 삭제
    suspend fun delete(bloodGlucoseData: BloodGlucoseData): Int =
        bloodGlucoseDataDao.delete(bloodGlucoseData)

    // 특정 날짜의 데이터를 가져오기
    suspend fun fetchByDate(date: LocalDateTime): List<BloodGlucoseData> =
        bloodGlucoseDataDao.fetchByDate(date)

    // 특정 날짜와 수치가 존재하는지 확인
    suspend fun exists(date: LocalDateTime, glucoseValue: Int): Boolean =
        bloodGlucoseDataDao.exists(date, glucoseValue)

    // 모든 데이터 삭제
    suspend fun clearAll() =
        bloodGlucoseDataDao.clearAll()
}
