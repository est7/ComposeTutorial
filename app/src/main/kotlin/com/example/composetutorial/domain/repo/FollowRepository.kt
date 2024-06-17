package com.example.composetutorial.domain.repo

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.coroutines.flow.Flow

interface FollowRepository {
    suspend fun getSubFollowList(type: String, page: Int): Flow<Result<List<ComposeTipsItemDTO>>>
    suspend fun getFollowConfigTabList(): Flow<Result<List<ComposeTipsItemDTO>>>
}