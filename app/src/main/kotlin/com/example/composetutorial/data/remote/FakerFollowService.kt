package com.example.composetutorial.data.remote

import android.app.Application
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class FakerFollowService(private val application: Application) : IFollowService {
    override suspend fun getComposeTipsList(): Result<ImmutableList<ComposeTipsItemDTO>> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            Result.success(
                parseJsonFileToComposeTipsItemList("")
            )
        }
    }

    override suspend fun getFollowConfigTabList(): Result<ImmutableList<ComposeTipsItemDTO>> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            Result.success(
                parseJsonFileToComposeTipsItemList("")
            )
        }
    }

    private suspend fun parseJsonFileToComposeTipsItemList(filePath: String): ImmutableList<ComposeTipsItemDTO> {
        val json = application.assets.open("compose-tips.json").bufferedReader().use { it.readText() }
        val decodeFromString = Json.decodeFromString<List<ComposeTipsItemDTO>>(json)
        return decodeFromString.toImmutableList()
    }
}