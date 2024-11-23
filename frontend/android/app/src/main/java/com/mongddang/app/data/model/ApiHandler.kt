package com.mongddang.app.data.model

import com.google.gson.Gson
import com.mongddang.app.data.model.response.ApiResponse
import com.mongddang.app.data.model.response.ErrorResponse
import retrofit2.Response

suspend fun <T> ApiHandler(
    apiResponse: suspend () -> Response<T>,
): ApiResponse<T> {
    runCatching{
        val response = apiResponse.invoke()
        val errorData = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        if (response.isSuccessful) {
            return ApiResponse.Success(response.body())
        } else {
            return ApiResponse.Error(
                errorCode = errorData.code?: "",
                errorMessage = errorData.message ?: "",
            )
        }
    }.onFailure {
        return ApiResponse.Error(errorMessage = it.message ?: "")
    }
    return ApiResponse.Init
}