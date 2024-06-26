package com.example.composetutorial.data

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.data.remote.IUserInfoService
import com.example.composetutorial.data.remote.UserInfoService
import com.example.composetutorial.domain.repo.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl(private val userInfoService: IUserInfoService) : MainRepository {
    override suspend fun getComposeTipsList(): Flow<Result<List<ComposeTipsItemDTO>>> = flow {
        userInfoService.getComposeTipsList().fold(onSuccess = {
            emit(Result.success(it))
        }, onFailure = {
            emit(Result.failure(it))
        })
    }

}
