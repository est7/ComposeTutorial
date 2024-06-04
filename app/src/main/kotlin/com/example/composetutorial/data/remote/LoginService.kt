package com.example.composetutorial.data.remote

import android.util.Log
import com.example.composetutorial.data.dto.UserInfoDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.parameters

class LoginService(private val client: HttpClient, private val baseUrl: String, private val path: String) : ApiService {
    suspend fun login(username: String, password: String): Result<Boolean> {
        return try {
            val request = client.get("$baseUrl/$path") {
                url {
                    parameters {
                        this.append("username", username)
                        this.append("password", password)
                    }
                }
            }
            val body = request.body<Boolean>()
            Result.success(body)
        } catch (e: Exception) {
            Log.e("Ktor Client", e.message ?: "[Nuh-uh] message")
            Result.failure(e)
        }
    }

    suspend fun getMyInfo(): Result<UserInfoDTO> {
        return Result.success(
            UserInfoDTO(
                "nick", 11, "https://avatars.githubusercontent.com/u/25135877?v=4", "这是我的简介"
            )
        )
    }

}
