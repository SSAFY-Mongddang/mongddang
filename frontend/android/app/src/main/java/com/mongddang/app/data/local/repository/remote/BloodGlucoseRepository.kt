package com.mongddang.app.data.local.repository.remote

import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
import com.mongddang.app.data.local.dao.api.BloodGlucoseResponse
import com.mongddang.app.data.local.dao.api.TestResponse
import kotlinx.coroutines.flow.Flow

interface BloodGlucoseRepository {
    suspend fun sendSamsungBloodGlucose(bloodGlucoseRequest: BloodGlucoseRequest): Flow<ApiResponse<BloodGlucoseResponse>>
    suspend fun getTest(): Flow<ApiResponse<TestResponse>>
}