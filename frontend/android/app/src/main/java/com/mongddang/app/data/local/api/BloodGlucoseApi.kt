//package com.mongddang.app.data.local.api
//
//import com.mongddang.app.data.local.dao.api.BloodGlucoseRequest
//import com.mongddang.app.data.local.dao.api.ResponseBody
//import com.mongddang.app.data.local.dao.api.BloodGlucoseResponse
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.GET
//import retrofit2.http.POST
//
//interface BloodGlucoseApi {
//    @POST("bloodsugar/send")
//    suspend fun sendBloodGlucose(
//        @Body bloodGlucoseRequest: BloodGlucoseRequest
//    ): Response<ResponseBody<BloodGlucoseResponse>>
//
//    @GET("bloodsugar/test")
//    suspend fun getTest():Response<ResponseBody<String>>
//}