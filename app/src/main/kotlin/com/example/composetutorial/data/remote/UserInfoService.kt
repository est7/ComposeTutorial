package com.example.composetutorial.data.remote

import android.util.Log
import com.example.composetutorial.data.ComposeTipsItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class UserInfoService(private val client: HttpClient, private val baseUrl: String, private val path: String) :
    ApiService {
    suspend fun getComposeTipsList(): Result<List<ComposeTipsItemDTO>> {
        return try {
            val request = client.get("$baseUrl/$path")
            val body = request.body<List<ComposeTipsItemDTO>>()
            Result.success(body)
        } catch (e: Exception) {
            Log.e("Ktor Client", e.message ?: "[Nuh-uh] message")
            Result.failure(e)
        }
    }
}
