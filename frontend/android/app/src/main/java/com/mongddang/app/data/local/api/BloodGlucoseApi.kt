package com.mongddang.app.data.local.api

import com.mongddang.app.data.local.dao.api.ResponseBody
import com.mongddang.app.data.local.entity.BloodGlucoseResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface BloodGlucoseApi {
    @POST("api/vital/bloodsugar/send")
    suspend fun sendBloodGlucose(): Response<ResponseBody<BloodGlucoseResponse>>
}