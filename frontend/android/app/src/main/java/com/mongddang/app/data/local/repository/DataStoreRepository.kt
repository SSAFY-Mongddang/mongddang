package com.mongddang.app.data.local.repository


import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun saveUserNickName(nickName: String)
    fun getAccessToken(): Flow<String?>
    fun getUserNickName(): Flow<String?>
}