package com.example.composetutorial.navagation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 *
 * @author: est8
 * @date: 2024/6/5
 */
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController provided") }
// val LocalNavController: ProvidableCompositionLocal<NavHostController?> = staticCompositionLocalOf { null }
