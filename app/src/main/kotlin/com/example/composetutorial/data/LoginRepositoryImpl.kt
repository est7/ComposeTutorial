package com.example.composetutorial.data

import androidx.datastore.core.DataStore
import com.example.composetutorial.data.dto.UserInfoDTO
import com.example.composetutorial.data.dto.UserPreferences
import com.example.composetutorial.data.local.UserPreferencesDataSource
import com.example.composetutorial.data.remote.LoginService
import com.example.composetutorial.domain.repo.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val loginService: LoginService,private val localDataStore:UserPreferencesDataSource) : LoginRepository {
    override suspend fun login(username: String, password: String): Result<Boolean> {
        return loginService.login(username, password)
    }

    override suspend fun invalidToken(): Result<Boolean> {
        return localDataStore.clearToken()
    }

    override suspend fun getMyInfo(token:String): Flow<Result<UserInfoDTO>> {
        return flow {
            val result = loginService.getMyInfo()
            result.fold(onSuccess = {
                emit(Result.success(it))
            }, onFailure = {
                emit(Result.failure(it))
            })
        }
    }
}