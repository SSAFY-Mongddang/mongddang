package com.mongddang.app.utils

import com.google.gson.Gson
import com.mongddang.app.data.local.dao.api.ApiResponse
import com.mongddang.app.data.local.dao.api.ErrorResponse
import retrofit2.Response

suspend fun <T> ApiHandler(
    apiResponse: suspend () -> Response<T>,
): ApiResponse<T> {
    runCatching {
        val response = apiResponse.invoke()
        val errorData = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        if (response.isSuccessful) {
            return ApiResponse.Success(response.body())
        } else {
            return ApiResponse.Error(
                errorCode = errorData.status ?: 0,
                errorMessage = errorData.message ?: "",
            )
        }
    }.onFailure {
        return ApiResponse.Error(errorMessage = it.message ?: "")
    }
    return ApiResponse.Init
}
