package com.mongddang.app.data.local.repository.remote

import android.util.Log
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
import com.mongddang.app.data.local.dao.api.BloodGlucoseResponse
import com.mongddang.app.data.local.dao.api.ResponseBody
import com.mongddang.app.data.local.dao.api.Status
import com.mongddang.app.data.local.dao.api.TestResponse
import com.mongddang.app.utils.ApiHandler
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import java.time.LocalDateTime
import javax.inject.Inject


private const val TAG = "BloodGlucoseRepositoryI"

class BloodGlucoseRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): BloodGlucoseRepository {
    @SerialName("id")
    val id: Long = 0L
    @SerialName("bloodSugarLevel")
    val bloodSugarLevel: Int = 0
    @SerialName("measurementTime")
    val measurementTime: LocalDateTime? = null
    override suspend fun sendSamsungBloodGlucose(
        bloodGlucoseRequest: BloodGlucoseRequest
    ): Flow<ApiResponse<BloodGlucoseResponse>> = flow {
        val response = ApiHandler {
            apiService.sendBloodGlucose(bloodGlucoseRequest) // 요청 객체 전달
        }
        when (response) {
                is ApiResponse.Success -> {
                    val message = "혈당 등록에 성공하였습니다."
                    response.body?.data?.let{
                        BloodGlucoseResponse(
                            id = it.id?: 0L,
                            bloodSugarLevel = it.bloodSugarLevel,
                            measurementTime = it.measurementTime,
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

    override suspend fun getTest(): Flow<ApiResponse<TestResponse>> = flow {
        val response = ApiHandler {
            apiService.getMessage() // API 호출
        }

        when (response) {
            is ApiResponse.Success -> {
                // body가 TestResponse 타입인지 확인
                val data = response.body as? String // ResponseBody<String>에서 String 추출
                val testResponse = TestResponse(testString = data ?: "")

                // ApiResponse.Success로 감싸기
                emit(ApiResponse.Success(body = testResponse))
                Log.d(TAG, "getTest: 성공 - $testResponse")
            }
            is ApiResponse.Error -> {
                Log.e(TAG, "getTest: 에러 발생 - ${response.errorMessage}")
                emit(ApiResponse.Error(code = "VT001", errorMessage = "테스트에 실패하였습니다."))
            }
            is ApiResponse.Init -> {
                emit(ApiResponse.Init)
            }
        }
    }

}