package com.example.composetutorial.di.module

import com.example.composetutorial.data.remote.LoginService
import com.example.composetutorial.data.remote.UserInfoService
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
val apiModule = module {
    single { createService<UserInfoService>(get(), "/userinfo") }
    single { createService<LoginService>(get(), "/login") }
    //...
}
