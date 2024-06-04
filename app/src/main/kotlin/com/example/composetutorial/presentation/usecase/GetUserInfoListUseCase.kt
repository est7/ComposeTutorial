package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.LoginRepository
import com.example.composetutorial.domain.repo.MainRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUserInfoListUseCase() : KoinComponent {
    private val repository: MainRepository by inject()
    suspend operator fun invoke(token: String) = repository.getComposeTipsList()
}