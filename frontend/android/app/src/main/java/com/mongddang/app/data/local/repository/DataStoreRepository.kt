package com.mongddang.app.data.local.repository


import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): Flow<String?>
    suspend fun saveUserNickName(nickName: String)

}