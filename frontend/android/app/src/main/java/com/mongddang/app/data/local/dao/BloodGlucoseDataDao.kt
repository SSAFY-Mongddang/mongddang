package com.mongddang.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongddang.app.data.local.entity.BloodGlucoseData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime


@Dao
interface BloodGlucoseDataDao {
    // 특정 ID로 데이터를 가져오기
    @Query("SELECT * FROM blood_glucose_data WHERE id = :id")
    fun loadDataById(id: Long): Flow<BloodGlucoseData>


    // 데이터 삽입 시 반환 타입을 Long으로 설정
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBloodGlucoseData(data: BloodGlucoseData): Long


    // 데이터 삭제 시 반환 타입을 Int로 설정 (삭제된 행의 수 반환)
    @Delete
    suspend fun delete(bloodGlucoseData: BloodGlucoseData): Int

    // 특정 시간 간격으로 데이터를 로드
    @Query("SELECT * FROM blood_glucose_data WHERE time BETWEEN :startTime AND :endTime")
    fun loadDataByTimeInterval(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<BloodGlucoseData>>

    // 특정 날짜의 데이터 가져오기
    @Query("SELECT * FROM blood_glucose_data WHERE time = :date")
    suspend fun getDataByDate(date: LocalDateTime): List<BloodGlucoseData>

    // 특정 날짜와 수치가 존재하는지 확인
    @Query("SELECT COUNT(*) FROM blood_glucose_data WHERE time = :date AND glucoseValue = :glucoseLevel")
    suspend fun isDataExists(date: LocalDateTime, glucoseLevel: Float): Int

    // DAO에 데이터 조회 메서드 추가
    @Query("SELECT * FROM blood_glucose_data WHERE id = :id")
    suspend fun getBloodGlucoseDataById(id: Long): BloodGlucoseData?
}