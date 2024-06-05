package com.example.composetutorial.domain.repo

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getComposeTipsList(): Flow<Result<List<ComposeTipsItemDTO>>>
}