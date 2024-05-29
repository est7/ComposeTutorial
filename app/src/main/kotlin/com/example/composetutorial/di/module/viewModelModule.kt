package com.example.composetutorial.di.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/29
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { YearGradesViewModel(get()) }
    viewModel { CalendarViewModel(get()) }
}