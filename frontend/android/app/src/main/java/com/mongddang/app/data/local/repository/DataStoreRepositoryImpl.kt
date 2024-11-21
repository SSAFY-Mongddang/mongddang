package com.mongddang.app.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val USER_NICKNAME_KEY = stringPreferencesKey("user_nickname")
    }

    // Override 메서드들
    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveUserNickName(nickName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NICKNAME_KEY] = nickName
        }
    }

    override fun getAccessToken(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY]
    }

    override fun getUserNickName(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_NICKNAME_KEY]
    }
}