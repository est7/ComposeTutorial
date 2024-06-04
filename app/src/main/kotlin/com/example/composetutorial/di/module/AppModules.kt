package com.example.composetutorial.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module


/**
 *
 * @author: est8
 * @date: 2024/5/27
 */

val appModules = listOf(
    networkModule,
    apiModule,
    viewModelModule,
    repositoryModule,
    storageModule,
    workerModule,
    commonModule,
    useCaseModule,
)

