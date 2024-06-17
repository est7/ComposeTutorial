package com.example.composetutorial.data.remote

import android.util.Log
import com.example.composetutorial.data.dto.ComposeTipsItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.collections.immutable.ImmutableList


class FollowService(private val client: HttpClient, private val baseUrl: String, private val path: String) :
    IFollowService {
    override suspend fun getComposeTipsList(): Result<ImmutableList<ComposeTipsItemDTO>> {
        return try {
            val request = client.get("$baseUrl/$path")
            val body = request.body<ImmutableList<ComposeTipsItemDTO>>()
            Result.success(body)
        } catch (e: Exception) {
            Log.e("Ktor Client", e.message ?: "[Nuh-uh] message")
            Result.failure(e)
        }
    }

    override suspend fun getFollowConfigTabList(): Result<ImmutableList<ComposeTipsItemDTO>> {
        return try {
            val request = client.get("$baseUrl/$path")
            val body = request.body<ImmutableList<ComposeTipsItemDTO>>()
            Result.success(body)
        } catch (e: Exception) {
            Log.e("Ktor Client", e.message ?: "[Nuh-uh] message")
            Result.failure(e)
        }
    }
}
