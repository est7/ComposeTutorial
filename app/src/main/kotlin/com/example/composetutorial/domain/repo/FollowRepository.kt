package com.example.composetutorial.domain.repo

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    suspend fun getSubFollowList(type: String, page: Int): Flow<Result<ImmutableList<ComposeTipsItemDTO>>>
    suspend fun getFollowConfigTabList(): Flow<Result<List<ComposeTipsItemDTO>>>
}