package com.example.composetutorial.data.remote

import android.app.Application
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.random.Random


class FakerFollowService(private val application: Application) : IFollowService {
    override suspend fun getComposeTipsList(): Result<ImmutableList<ComposeTipsItemDTO>> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            // 随机返回一个
            if (Random.nextInt(0, 10) > 2) {
                Result.success(
                    parseJsonFileToComposeTipsItemList("")
                )
            } else {
                Result.failure(Exception("Network Error ,Please try again later"))
            }
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
        // 返回随机长度
        Random.nextInt(0, 10).let {
            return if (it < 7) {
                decodeFromString.toImmutableList()
            } else {
                decodeFromString.subList(0, 18).toImmutableList()
            }
        }
    }
}