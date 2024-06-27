package com.example.composetutorial.utils

import android.app.Application

/**
 *
 * @author: est8
 * @date: 2024/6/27
 */
class ResourceProviderUtils(private val application: Application) {
    fun getString(id: Int) = application.getString(id)
    fun getString(id: Int, vararg args: Any) = application.getString(id, *args)
}