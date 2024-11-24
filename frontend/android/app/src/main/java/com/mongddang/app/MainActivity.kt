package com.mongddang.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.getcapacitor.BridgeActivity
import com.mongddang.app.data.repository.local.DataStoreRepository
import com.mongddang.app.data.repository.remote.UserRepository
import com.mongddang.app.plugin.UserInfoPlugin
import com.mongddang.app.plugin.ForegroundPlugin
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BridgeActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        // ExamplePlugin 인스턴스 생성 후 메서드 호출
        registerPlugin(ForegroundPlugin::class.java)
        registerPlugin(UserInfoPlugin::class.java)

        super.onCreate(savedInstanceState)
    }
}