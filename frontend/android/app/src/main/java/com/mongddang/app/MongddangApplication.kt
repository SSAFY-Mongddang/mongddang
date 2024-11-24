package com.mongddang.app

import android.app.Application
import com.mongddang.app.data.api.UserInfoApiService
import com.mongddang.app.data.repository.local.DataStoreRepository
import com.mongddang.app.data.repository.remote.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MongddangApplication: Application() {
}