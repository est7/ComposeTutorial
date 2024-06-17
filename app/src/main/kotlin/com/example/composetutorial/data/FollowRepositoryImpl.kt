package com.example.composetutorial.data

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import com.example.composetutorial.data.remote.IFollowService
import com.example.composetutorial.domain.repo.FollowRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FollowRepositoryImpl(private val followService: IFollowService) : FollowRepository {

    override suspend fun getSubFollowList(type: String, page: Int): Flow<Result<ImmutableList<ComposeTipsItemDTO>>> =
        flow {
            followService.getComposeTipsList().fold(onSuccess = {
                emit(Result.success(it))
            }, onFailure = {
                emit(Result.failure(it))
            })
        }

    override suspend fun getFollowConfigTabList(): Flow<Result<ImmutableList<ComposeTipsItemDTO>>> = flow {
        followService.getFollowConfigTabList().fold(onSuccess = {
            emit(Result.success(it))
        }, onFailure = {
            emit(Result.failure(it))
        })
    }


}
