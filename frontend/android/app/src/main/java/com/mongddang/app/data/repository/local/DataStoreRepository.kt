package com.mongddang.app.data.repository.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.mongddang.app.data.model.response.UserInfoResponse

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
        val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL_KEY")
        val USER_NICKNAME_KEY = stringPreferencesKey("USER_NICKNAME_KEY")
        val USER_ROLE_KEY = stringPreferencesKey("USER_ROLE_KEY")

    }

    suspend fun saveUser(userInfo: UserInfoResponse) {
        dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = userInfo.email
            prefs[USER_NICKNAME_KEY] = userInfo.nickname
            prefs[USER_ROLE_KEY] = userInfo.role
        }
    }

    suspend fun validateAndSaveUserInfo(data: UserInfoResponse?): UserInfoResponse? {
        return data?.takeIf {
            !it.email.isNullOrBlank() && !it.nickname.isNullOrBlank() && !it.role.isNullOrBlank()
        }?.also { validData ->
            saveUser(validData)
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { prefs -> prefs[ACCESS_TOKEN_KEY] = token }
    }

    suspend fun saveUserNickname(nickname: String) {
        dataStore.edit { prefs -> prefs[USER_NICKNAME_KEY] = nickname }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { prefs -> prefs[USER_EMAIL_KEY] = email}
    }

    suspend fun saveUserRole(role: String) {
        dataStore.edit { prefs -> prefs[USER_ROLE_KEY] = role}
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { prefs -> prefs.remove(ACCESS_TOKEN_KEY) }
    }

    suspend fun deleteUserNickname() {
        dataStore.edit { prefs -> prefs.remove(USER_NICKNAME_KEY) }
    }

    suspend fun deleteUserEmail() {
        dataStore.edit { prefs -> prefs.remove(USER_EMAIL_KEY) }
    }

    suspend fun deleteUserRole() {
        dataStore.edit { prefs -> prefs.remove(USER_ROLE_KEY) }
    }


    fun getAccessToken(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[ACCESS_TOKEN_KEY] }

    fun getUserNickname(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[USER_NICKNAME_KEY] }

    fun getUserEmail(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[USER_EMAIL_KEY] }

    fun getUserRole(): Flow<String?> =
        dataStore.data.map { prefs -> prefs[USER_ROLE_KEY] }

}