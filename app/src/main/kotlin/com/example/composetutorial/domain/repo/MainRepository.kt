package com.example.composetutorial.domain.repo

import com.example.composetutorial.data.ComposeTipsItemDTO
import com.example.composetutorial.data.dto.UserInfoDTO
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getComposeTipsList(): Flow<Result<List<ComposeTipsItemDTO>>>
}