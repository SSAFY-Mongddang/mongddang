package com.mongddang.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.getcapacitor.BridgeActivity
import com.mongddang.app.healthdata.viewmodel.HealthMainViewModel
import com.mongddang.app.healthdata.viewmodel.HealthViewModelFactory
import com.mongddang.app.healthdata.SamsungHealthPlugin
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BridgeActivity(){

    private lateinit var healthMainViewModel: HealthMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        // ExamplePlugin 인스턴스 생성 후 메서드 호출
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(SamsungHealthPlugin::class.java)
        registerPlugin(BloodGlucosePlugin::class.java)
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