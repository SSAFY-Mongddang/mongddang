package com.mongddang.app.data.local.repository.remote

import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
import com.mongddang.app.data.local.dao.api.BloodGlucoseResponse
import com.mongddang.app.data.local.dao.api.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/vital/bloodsugar/test")
    suspend fun getMessage(): Response<String>

    @POST("api/vital/bloodsugar/current/send")
    suspend fun sendBloodGlucose(
        @Body bloodGlucoseRequest: BloodGlucoseRequest
    ): Response<ResponseBody<BloodGlucoseResponse>>

}