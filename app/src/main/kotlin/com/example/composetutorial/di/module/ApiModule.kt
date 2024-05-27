package com.example.composetutorial.di.module

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/27
 */

val apiModule = module {
    single { provideKtorClient() }
}

fun provideKtorClient(): HttpClient = HttpClient {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("KTOR", message)
            }
        }
        level = LogLevel.ALL // logs everything
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 10_000 // 10s
        connectTimeoutMillis = 10_000 // 10s
    }

    install(ContentNegotiation) {
        gson(
            contentType = ContentType.Any // workaround for broken APIs
        )
    }
}