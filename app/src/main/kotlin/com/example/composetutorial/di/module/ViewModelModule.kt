package com.example.composetutorial.di.module

import com.example.composetutorial.presentation.feature.tips_06.Tips06ViewModel
import com.example.composetutorial.presentation.feature.tips_17.Tips17ViewModel
import com.example.composetutorial.presentation.viewmodel.FollowViewModel
import com.example.composetutorial.presentation.viewmodel.LoginViewModel
import com.example.composetutorial.presentation.viewmodel.MainViewModel
import com.example.composetutorial.presentation.viewmodel.SubFollowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/29
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { Tips06ViewModel(get()) }
    viewModel { Tips17ViewModel(get()) }
    viewModel { FollowViewModel(get(), get()) }
    viewModel { SubFollowViewModel(get(), get()) }

}