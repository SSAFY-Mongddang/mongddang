package com.mongddang.app.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun getAccessToken() =
        flow {
            emit(
                dataStore.data
                    .map { prefs ->
                        prefs[ACCESS_TOKEN_KEY]
                    }.first(),
            )
        }

    override suspend fun saveUserNickName(nickName: String){
        dataStore.edit { prefs ->
            prefs[USER_NICKNAME] = nickName
        }
    }


    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
        val USER_NICKNAME = stringPreferencesKey("USER_NICKNAME")
    }

}