package com.mongddang.app.data.local.dao.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
    @SerialName("testString")
    val testString: String
)