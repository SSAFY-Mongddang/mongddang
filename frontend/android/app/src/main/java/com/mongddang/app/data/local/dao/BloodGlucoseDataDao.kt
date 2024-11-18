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

    // 특정 ID로 데이터를 가져오기 (Flow)
    @Query("SELECT * FROM blood_glucose_data WHERE id = :id")
    fun fetchByIdFlow(id: Long): Flow<BloodGlucoseData?>

    // 특정 ID로 데이터를 가져오기 (suspend)
    @Query("SELECT * FROM blood_glucose_data WHERE id = :id")
    suspend fun fetchById(id: Long): BloodGlucoseData?

    // 데이터 삽입 (Long 반환: 삽입된 행의 ID)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: BloodGlucoseData): Long

    // 데이터 삭제 (Int 반환: 삭제된 행의 수)
    @Delete
    suspend fun delete(data: BloodGlucoseData): Int

    // 특정 시간 간격 내 데이터를 가져오기
    @Query("SELECT * FROM blood_glucose_data WHERE time BETWEEN :startTime AND :endTime ORDER BY time ASC")
    fun fetchByTimeInterval(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<BloodGlucoseData>>

    // 특정 날짜 데이터를 가져오기
    @Query("SELECT * FROM blood_glucose_data WHERE time = :date")
    suspend fun fetchByDate(date: LocalDateTime): List<BloodGlucoseData>

    // 특정 날짜와 수치가 존재하는지 확인
    @Query("SELECT COUNT(*) > 0 FROM blood_glucose_data WHERE time = :date AND glucoseValueMgPerDl = :glucoseValue")
    suspend fun exists(date: LocalDateTime, glucoseValue: Int): Boolean

    // 전체 데이터 삭제 (Optional 추가)
    @Query("DELETE FROM blood_glucose_data")
    suspend fun clearAll()
}
