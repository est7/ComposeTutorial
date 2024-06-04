package com.example.composetutorial.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composetutorial.navagation.Destination
import com.example.composetutorial.presentation.page.FollowScreen
import com.example.composetutorial.presentation.page.MainScreen
import com.example.composetutorial.presentation.page.MyProfileScreen
import com.example.composetutorial.presentation.page.SettingScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = Destination.Main.route, modifier = modifier
    ) {
        composable(route = Destination.Main.route, enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down,
                initialOffset = { it / 4 }) + fadeIn()
        }, exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, targetOffset = { it / 4 }) + fadeOut()
        }) {
            MainScreen(onNavigate = { destination ->
                navController.navigateTo(destination.route)
            })
        }

        composable(route = Destination.Follow.route, enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, initialOffset = { it / 4 }) + fadeIn()
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                targetOffset = { it / 4 }) + fadeOut()
        }) {
            FollowScreen()
        }

        composable(route = Destination.Settings.route, enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, initialOffset = { it / 4 }) + fadeIn()
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                targetOffset = { it / 4 }) + fadeOut()
        }) {
            SettingScreen()
        }

        composable(route = Destination.MyProfile.route, enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, initialOffset = { it / 4 }) + fadeIn()
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                targetOffset = { it / 4 }) + fadeOut()
        }) {
            MyProfileScreen()
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}