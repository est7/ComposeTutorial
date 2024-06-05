package com.example.composetutorial.data.remote

import android.app.Application
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class FakerUserInfoService(private val application: Application) : IUserInfoService {
    override suspend fun getComposeTipsList(): Result<List<ComposeTipsItemDTO>> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            Result.success(
                parseJsonFileToComposeTipsItemList("")
            )
        }
    }

    private suspend fun parseJsonFileToComposeTipsItemList(filePath: String): List<ComposeTipsItemDTO> {
        val json = application.assets.open("compose-tips.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(json)
    }
}