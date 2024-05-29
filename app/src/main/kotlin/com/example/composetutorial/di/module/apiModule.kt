package com.example.composetutorial.di.module

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/5/27
 */

val apiModule = module {
    single { provideKtorClient() }
}

fun provideKtorClient(): HttpClient = HttpClient(CIO) {
    engine {
        maxConnectionsCount = 1000
        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
        // https {
        // this: TLSConfigBuilder
        // serverName = "api.ktor.io"
        // cipherSuites = CIOCipherSuites.SupportedSuites
        // trustManager = myCustomTrustManager
        // random = mySecureRandom
        // addKeyStore(myKeyStore, myKeyStorePassword)
        // }

    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("KTOR", message)
            }
        }
        level = LogLevel.ALL // logs everything
        // filter { request ->
        // request.url.host.contains("ktor.io")
        // }
    }

    // ContentEncoding {
    //     gzip()
    //     deflate()
    // }

    install(HttpTimeout) {
        requestTimeoutMillis = 10_000 // 10s
        connectTimeoutMillis = 10_000 // 10s
    }

    install(ContentNegotiation) {
        json(Json {
            // 配置Json实例
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}