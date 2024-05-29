package com.example.composetutorial.di.module

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/29
 */
val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(androidContext()) }
    single<LoginRepository> { LoginRepositoryImpl() }
}

interface MainRepository {

}
