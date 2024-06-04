package com.example.composetutorial.presentation.usecase

import com.example.composetutorial.domain.repo.LoginRepository

class UpdateMyInfoUseCase(private val loginRepository: LoginRepository){
    suspend operator fun invoke(token:String) = loginRepository.getMyInfo(token)
}