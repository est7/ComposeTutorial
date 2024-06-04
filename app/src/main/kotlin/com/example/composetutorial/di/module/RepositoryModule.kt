package com.example.composetutorial.di.module

import com.example.composetutorial.data.LoginRepositoryImpl
import com.example.composetutorial.data.MainRepositoryImpl
import com.example.composetutorial.domain.repo.LoginRepository
import com.example.composetutorial.domain.repo.MainRepository
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/29
 */
val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single<MainRepository> { MainRepositoryImpl(get()) }
}

