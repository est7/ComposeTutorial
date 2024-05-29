package com.example.composetutorial.di.module

import com.example.composetutorial.repo.LoginRepository
import com.example.composetutorial.repo.LoginRepositoryImpl
import com.example.composetutorial.repo.MainRepository
import com.example.composetutorial.repo.MainRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/29
 */
val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(androidContext()) }
    single<LoginRepository> { LoginRepositoryImpl }
}

