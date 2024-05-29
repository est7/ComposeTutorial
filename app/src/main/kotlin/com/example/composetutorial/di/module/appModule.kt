package com.example.composetutorial.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.ktor.client.plugins.contentnegotiation.*
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/27
 */

val appModule = module {
    single(createdAtStart = true) { ExceptionHandler(androidApplication()) }
    single(createdAtStart = true) { AppPreferences(androidApplication()) }
}

val sharePrefUtilModule = module {
    single { getSharedPreferences() }

    single { getSettingPreference() }

    single(createdAtStart = true) { getCityPreference() }
}

private fun Scope.getCityPreference(): SelectedCityPreference {
    return SelectedCityPreference(get(), get())
}

private fun Scope.getSettingPreference(): SettingPreferences {
    return SettingPreferences(get(), androidApplication())
}

private fun Scope.getSharedPreferences(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(androidApplication())
}


class ExceptionHandler(androidApplication: Application) {
    fun handleException(e: Exception) {

    }
}
class AppPreferences(androidApplication: Application) {

}
