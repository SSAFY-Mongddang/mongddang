package com.mongddang.app

import android.content.Intent
import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val TAG = "BloodGlucosePlugin"

@CapacitorPlugin(name="BloodGlucosePlugin")
class BloodGlucosePlugin : Plugin(){
    @PluginMethod
    fun getThisTimeBloodGlucose(call: PluginCall) {
        // 요청된 날짜 문자열 가져오기
        val requestDateTimeString = call.getString("datetime")
        if (requestDateTimeString == null) {
            val errorMessage = "datetime 값이 누락되었습니다."
            Log.d(TAG, errorMessage)
            call.reject(errorMessage)
            return
        }

        // LocalDateTime 변환
        val requestLocalDateTime = try {
            LocalDateTime.parse(requestDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: DateTimeParseException) {
            val errorMessage = "잘못된 날짜 형식입니다. ISO_LOCAL_DATE_TIME 형식이어야 합니다: $requestDateTimeString"
            Log.d(TAG, errorMessage, e)
            call.reject(errorMessage)
            return
        }

        // 최소 및 현재 날짜 설정
        val minimumDate = LocalDateTime.of(2020, 1, 1, 0, 0) // 2020년 1월 1일
        val currentDate = LocalDateTime.now()

        // 날짜 유효성 검사
        if (requestLocalDateTime.isBefore(minimumDate) || requestLocalDateTime.isAfter(currentDate)) {
            val errorMessage = "유효하지 않은 날짜입니다: $requestLocalDateTime"
            Log.d(TAG, errorMessage)
            call.reject(errorMessage)
            return
        }

        // 유효한 날짜일 경우 처리
        val response = JSObject().apply {
            put("datetime", requestLocalDateTime.toString())
            put("status", "valid")
        }
        Log.d(TAG, "getThisTimeBloodGlucose: $requestLocalDateTime 는 유효한 날짜입니다.")

        // Intent를 통한 새로운 Activity 시작
        val context = getActivity() ?: run {
            val errorMessage = "Context를 가져올 수 없습니다."
            Log.d(TAG, errorMessage)
            call.reject(errorMessage)
            return
        }

        val intent = Intent(context, BloodGlucoseActivity::class.java).apply {
            putExtra("dateTime", requestLocalDateTime.toString()) // 전달할 dateTime 값
        }

        context.startActivity(intent)

        // 성공적으로 날짜가 유효하고 처리 완료된 경우 응답
        call.resolve(response)
    }

}