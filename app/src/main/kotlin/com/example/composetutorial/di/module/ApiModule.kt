package com.example.composetutorial.di.module

import com.example.composetutorial.data.remote.FakerUserInfoService
import com.example.composetutorial.data.remote.IUserInfoService
import com.example.composetutorial.data.remote.LoginService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
val apiModule = module {
    single<IUserInfoService> { FakerUserInfoService(androidApplication()) }
    // single<IUserInfoService> { createService<UserInfoService>(get(), "/userinfo") }
    single { createService<LoginService>(get(), "/login") }
    //...
}
