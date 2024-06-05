package com.example.composetutorial.data.remote

import com.example.composetutorial.data.dto.ComposeTipsItemDTO

interface IUserInfoService : ApiService {
    suspend fun getComposeTipsList(): Result<List<ComposeTipsItemDTO>>
}