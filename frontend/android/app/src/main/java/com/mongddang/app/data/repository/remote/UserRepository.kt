package com.mongddang.app.data.repository.remote

import com.mongddang.app.data.model.response.ApiResponse
import com.mongddang.app.data.model.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.mongddang.app.data.api.UserInfoApiService
import kotlinx.coroutines.flow.flow
import com.mongddang.app.data.model.ApiHandler
import com.mongddang.app.data.repository.local.DataStoreRepository

class UserRepository @Inject constructor(
    private val userInfoApiService: UserInfoApiService,
    private val dataStoreRepository: DataStoreRepository
){
    private suspend fun validateAndSaveUserInfo(data: UserInfoResponse?): UserInfoResponse? {
        return data?.takeIf {
            !it.email.isNullOrBlank() && !it.nickname.isNullOrBlank() && !it.role.isNullOrBlank()
        }?.also { validData ->
            dataStoreRepository.saveUser(validData)
        }
    }

    suspend fun getUserInfo(): Flow<ApiResponse<UserInfoResponse>> = flow {
        val response = ApiHandler { userInfoApiService.getUserInfo() }
        when (response) {
            is ApiResponse.Success -> {
                val userInfoResponse = validateAndSaveUserInfo(response.body?.data)
                if (userInfoResponse != null) {
                    emit(ApiResponse.Success(userInfoResponse))
                } else {
                    emit(ApiResponse.Error(errorCode = "U000", errorMessage = "사용자 정보가 없습니다."))
                }
            }
            ApiResponse.Init -> {
                // Init 상태는 특별히 처리하지 않음
            }
            is ApiResponse.Error -> {
                emit(ApiResponse.Error(errorCode = response.errorCode, errorMessage = response.errorMessage))
            }
        }
    }
}