package com.example

import android.app.Application
import com.example.composetutorial.di.module.apiModule
import com.example.composetutorial.di.module.appModule
import com.example.composetutorial.di.module.repositoryModule
import com.example.composetutorial.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 *
 * @author: est8
 * @date: 2024/5/27
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            androidFileProperties()
            modules(
                appModule,
                apiModule,
                repositoryModule,
                viewModelModule,
            )
        }
    }
}




