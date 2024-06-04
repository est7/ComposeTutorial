package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.LoginRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LogoutUseCase(private val loginRepository: LoginRepository){
    suspend operator fun invoke() = loginRepository.invalidToken()
}