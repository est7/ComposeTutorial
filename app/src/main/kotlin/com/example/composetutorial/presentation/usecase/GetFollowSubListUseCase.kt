package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.MainRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowSubListUseCase() : KoinComponent {
    private val repository: MainRepository by inject()
    suspend operator fun invoke(type: String, page: Int) = repository.getComposeTipsList()
}