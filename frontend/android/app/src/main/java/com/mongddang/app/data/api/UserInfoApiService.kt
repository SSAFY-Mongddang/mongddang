package com.mongddang.app.data.api

import com.mongddang.app.data.model.ResponseBody
import com.mongddang.app.data.model.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserInfoApiService {
    @GET("api/user/info")
    suspend fun getUserInfo(): Response<ResponseBody<UserInfoResponse>>

}