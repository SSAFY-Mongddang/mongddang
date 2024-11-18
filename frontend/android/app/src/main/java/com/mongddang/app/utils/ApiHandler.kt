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
        val code = response.code()
        val message = response.message()
        val errorData = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        if (response.isSuccessful) {
            return ApiResponse.Success(code, message, response.body())
        } else {
            return ApiResponse.Error(
                code = errorData.code ?: "",
                errorMessage = errorData.message ?: "",
            )
        }
    }.onFailure {
        return ApiResponse.Error(errorMessage = it.message ?: "")
    }
    return ApiResponse.Init
}
