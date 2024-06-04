package com.example.composetutorial.di.module

import com.example.composetutorial.data.local.UserPreferencesDataSource
import com.example.composetutorial.utils.ExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */

val commonModule = module {
    single(createdAtStart = true) { ExceptionHandler(get()) }
}