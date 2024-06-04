package com.example

import android.app.Application
import com.example.composetutorial.di.module.appModules
import com.example.composetutorial.utils.ExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

/**
 *
 * @author: est8
 * @date: 2024/5/27
 */
class MainApplication : Application(), KoinComponent {
    private val exceptionHandler: ExceptionHandler by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            androidFileProperties()
            workManagerFactory()
            modules(appModules)
        }

        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
    }


}




