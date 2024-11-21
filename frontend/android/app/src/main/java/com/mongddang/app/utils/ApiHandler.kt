package com.mongddang.app.utils

import android.util.Log
import com.google.gson.Gson
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.ErrorResponse
import retrofit2.Response

suspend fun <T> ApiHandler(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()

        // 요청 URL 로그
        Log.d("API Request", "Request URL: ${response.raw().request.url}")

        // 요청 메서드 로그
        Log.d("API Request", "Request Method: ${response.raw().request.method}")

        // 응답 상태 코드 로그
        Log.d("API Response", "Response Code: ${response.code()}")
        Log.d("API Response", "Response Message: ${response.message()}")

        if (response.isSuccessful) {
            ApiResponse.Success(code = 200, message="테스트 성공", body = response.body())
        } else {
            ApiResponse.Error(
                code = response.code().toString(),
                errorMessage = response.message()
            )
        }
    } catch (e: Exception) {
        Log.e("API Error", "Network call failed: ${e.message}", e)
        ApiResponse.Error(
            code = "NETWORK_ERROR",
            errorMessage = "Network call failed: ${e.message}"
        )
    }
}

