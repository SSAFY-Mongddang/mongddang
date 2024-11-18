package com.mongddang.app.data.local.dao.api

sealed class ApiResponse<out T : Any?> {
    data class Success<out T : Any?>(
        val code: Int = 200,
        val message: String? = "",
        val body: T?,
    ) : ApiResponse<T>()

    data class Error(
        val code: String = "",
        val errorMessage: String = "",
    ) : ApiResponse<Nothing>()

    data object Init : ApiResponse<Nothing>()
}

