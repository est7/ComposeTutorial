package com.example.composetutorial.domain.repo

import com.example.composetutorial.data.dto.UserInfoDTO
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Boolean>

    //失效 token
    suspend fun  invalidToken(): Result<Boolean>

    suspend fun getMyInfo(token:String): Flow<Result<UserInfoDTO>>
}