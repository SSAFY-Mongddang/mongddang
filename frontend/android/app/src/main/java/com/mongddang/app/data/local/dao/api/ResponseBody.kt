package com.mongddang.app.data.local.dao.api

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody<T>(
    val data: T?,
)
