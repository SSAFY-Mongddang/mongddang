package com.mongddang.app.data.local.dao.api

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int? = 0,
    val httpStatus: String? = "",
    val code: String? = "",
    val message: String? = "",
)


