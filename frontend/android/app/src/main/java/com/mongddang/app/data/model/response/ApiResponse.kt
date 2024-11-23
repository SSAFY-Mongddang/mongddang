package com.mongddang.app.data.model.response

sealed class ApiResponse<out T : Any?> {
    data class Success<out T : Any?>(
        val body: T?,
    ) : ApiResponse<T>()

    data class Error(
        val errorCode: String = "",
        val errorMessage: String = "",
    ) : ApiResponse<Nothing>()

    data object Init : ApiResponse<Nothing>()
}

