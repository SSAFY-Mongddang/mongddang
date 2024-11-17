package com.mongddang.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.getcapacitor.BridgeActivity


private const val TAG = "MainActivity"

class MainActivity : BridgeActivity(){

    private lateinit var healthMainViewModel: HealthMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        // ExamplePlugin 인스턴스 생성 후 메서드 호출
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        super.onCreate(savedInstanceState)

        healthMainViewModel = ViewModelProvider(
            this, HealthViewModelFactory(this)
        )[HealthMainViewModel::class.java]

        /** Show toast on exception occurrence **/
        healthMainViewModel.exceptionResponse.observe(this) { message ->
            Log.d(TAG, "healthMainViewModel.exceptionResponse: $message")
        }
    }
}