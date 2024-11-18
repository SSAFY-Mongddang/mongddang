package com.mongddang.app.data.local.dao.api

data class ErrorResponse(
    val status: Int? = 0,
    val httpStatus: String? = "",
    val code: String? = "",
    val message: String? = "",
)


