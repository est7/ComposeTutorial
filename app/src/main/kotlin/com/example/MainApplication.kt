package com.example

import android.app.Application
import android.system.Os.bind
import com.example.composetutorial.di.module.apiModule
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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

val appModule = module {
    single(createdAtStart = true) { ExceptionHandler(androidApplication()) }
    single(createdAtStart = true) { AppPreferences(androidApplication()) }
}

val repositoryModule = module {
    single<ExchangeRatesRepository> { ExchangeRatesRepositoryImpl(androidContext()) }
    single<LoginRepository> { LoginRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { YearGradesViewModel(get()) }
    viewModel { CalendarViewModel(get()) }
}