package com.example.composetutorial.di.module

import com.example.composetutorial.data.local.ColorItemDataSource
import com.example.composetutorial.data.local.UserPreferencesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
val storageModule = module {
    single(createdAtStart = true) {
        UserPreferencesDataSource(
            androidContext()
        )
    }
    // single { MessageDatabase(androidApplication()) }
    single{ColorItemDataSource()}
}