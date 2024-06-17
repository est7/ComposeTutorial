package com.example.composetutorial.di.module

import com.example.composetutorial.presentation.usecase.GetFollowListConfigUseCase
import com.example.composetutorial.presentation.usecase.GetFollowSubListUseCase
import com.example.composetutorial.presentation.usecase.GetUserInfoListUseCase
import com.example.composetutorial.presentation.usecase.LoginUseCase
import com.example.composetutorial.presentation.usecase.LogoutUseCase
import com.example.composetutorial.presentation.usecase.UpdateMyInfoUseCase
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
val useCaseModule = module {
    factory { LoginUseCase() }
    factory { LogoutUseCase(get()) }
    factory { UpdateMyInfoUseCase(get()) }
    factory { GetUserInfoListUseCase() }
    factory { GetFollowListConfigUseCase() }
    factory { GetFollowSubListUseCase() }
}