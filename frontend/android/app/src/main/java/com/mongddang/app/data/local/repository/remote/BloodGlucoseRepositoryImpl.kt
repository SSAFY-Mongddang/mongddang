package com.mongddang.app.data.local.repository.remote

import com.mongddang.app.data.local.api.BloodGlucoseApi
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.entity.BloodGlucoseResponse
import com.mongddang.app.data.local.entity.Status
import com.mongddang.app.utils.ApiHandler
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import java.time.LocalDateTime
import javax.inject.Inject

class BloodGlucoseRepositoryImpl @Inject constructor(
    private val bloodGlucoseApi: BloodGlucoseApi
): BloodGlucoseRepository {
    @SerialName("id")
    val id: Long = 0L
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null
    @SerialName("status")
    val status: Status? = null
    @SerialName("notification")
    val notification: Boolean = false
   override suspend fun sendSamsungBloodGlucose(): Flow<ApiResponse<BloodGlucoseResponse>> =
        flow{
            val response =
                ApiHandler{
                    bloodGlucoseApi.sendBloodGlucose()
                }
            when (response) {
                is ApiResponse.Success -> {
                    val message = "혈당 등록에 성공하였습니다."
                    response.body?.data?.let{
                        BloodGlucoseResponse(
                            id = it.id?: 0L,
                            bloodSugarLevel = it.bloodSugarLevel,
                            measurementTime = it.measurementTime,
                            status = it.status,
                            notification = it.notification?:false
                        )
                    }
                }
                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            code = "VT001",
                            errorMessage = "5분마다 혈당등록에 실패하였습니다.",
                        ),
                    )
                }
                is ApiResponse.Init -> {
                    emit(ApiResponse.Init)
                }
            }
        }

}