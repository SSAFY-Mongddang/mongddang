package com.mongddang.app.data.model.response

import android.provider.ContactsContract.CommonDataKinds.Nickname
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("role")
    val role: String,
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String
)
