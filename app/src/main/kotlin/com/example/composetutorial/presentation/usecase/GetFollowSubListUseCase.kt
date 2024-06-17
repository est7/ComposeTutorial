package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.FollowRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowSubListUseCase() : KoinComponent {
    private val repository: FollowRepository by inject()
    suspend operator fun invoke(type: String, page: Int) = repository.getSubFollowList(type, page)
}