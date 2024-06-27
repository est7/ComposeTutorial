package com.example.composetutorial.di.module

import com.example.composetutorial.utils.ExceptionHandler
import com.example.composetutorial.utils.MyDateUtils
import com.example.composetutorial.utils.ResourceProviderUtils
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */

val commonModule = module {
    single(createdAtStart = true) { ExceptionHandler(get()) }
    single(createdAtStart = true) { ResourceProviderUtils(get()) }
}