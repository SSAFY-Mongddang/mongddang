package com.mongddang.app.plugin

import com.getcapacitor.annotation.CapacitorPlugin
import com.mongddang.app.data.local.repository.DataStoreRepositoryImpl
import javax.inject.Inject

private const val TAG = "HiltAccessTokenPlugin"

@CapacitorPlugin(name = "AccessTokenPlugin")
class HiltAccessTokenPlugin : AccessTokenPlugin() {

    @Inject
    lateinit var injectedDataStoreRepositoryImpl: DataStoreRepositoryImpl

    override fun load() {
        super.load()
        this.dataStoreRepositoryImpl = injectedDataStoreRepositoryImpl
    }
}