package com.example.composetutorial.data.remote

import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.collections.immutable.ImmutableList

interface IFollowService : ApiService {
    suspend fun getComposeTipsList(): Result<ImmutableList<ComposeTipsItemDTO>>
    suspend fun getFollowConfigTabList(): Result<ImmutableList<ComposeTipsItemDTO>>
}