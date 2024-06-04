package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.LoginRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
class LoginUseCase : KoinComponent {
    private val repository: LoginRepository by inject()
    suspend operator fun invoke(username: String, password: String) = repository.login(username, password)
}