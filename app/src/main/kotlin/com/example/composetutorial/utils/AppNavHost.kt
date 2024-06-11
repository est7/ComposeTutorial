package com.example.composetutorial.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composetutorial.navagation.Destination
import com.example.composetutorial.presentation.feature.tips_01.Tips01Screen
import com.example.composetutorial.presentation.feature.tips_02.Tips02Screen
import com.example.composetutorial.presentation.feature.tips_03.Tips03Screen
import com.example.composetutorial.presentation.feature.tips_04.Tips04Screen
import com.example.composetutorial.presentation.feature.tips_05.Tips05Screen
import com.example.composetutorial.presentation.feature.tips_05.Tips05aScreen
import com.example.composetutorial.presentation.feature.tips_06.Tips06Screen
import com.example.composetutorial.presentation.feature.tips_07.Tips07Screen
import com.example.composetutorial.presentation.feature.tips_08.Tips08Screen
import com.example.composetutorial.presentation.feature.tips_09.Tips09Screen
import com.example.composetutorial.presentation.feature.tips_10.Tips10Screen
import com.example.composetutorial.presentation.feature.tips_11.Tips11Screen
import com.example.composetutorial.presentation.feature.tips_12.Tips12Screen
import com.example.composetutorial.presentation.feature.tips_13.Tips13Screen
import com.example.composetutorial.presentation.feature.tips_14.Tips14Screen
import com.example.composetutorial.presentation.feature.tips_15.Tips15Screen
import com.example.composetutorial.presentation.feature.tips_16.Tips16Screen
import com.example.composetutorial.presentation.feature.tips_17.Tips17Screen
import com.example.composetutorial.presentation.feature.tips_17.Tips17aScreen
import com.example.composetutorial.presentation.feature.tips_18.Tips18Screen
import com.example.composetutorial.presentation.feature.tips_20.Tips19Screen
import com.example.composetutorial.presentation.feature.tips_20.Tips20Screen
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

        createRoute(Destination.Tips_01.route) {
            Tips01Screen()
        }
        createRoute(Destination.Tips_02.route) {
            Tips02Screen()
        }
        createRoute(Destination.Tips_03.route) {
            Tips03Screen()
        }
        createRoute(Destination.Tips_04.route) {
            Tips04Screen()
        }
        createRoute(Destination.Tips_05.route) {
            Tips05Screen()
        }
        createRoute(Destination.Tips_05a.route) {
            Tips05aScreen()
        }
        createRoute(Destination.Tips_06.route) {
            Tips06Screen()
        }
        createRoute(Destination.Tips_07.route) {
            Tips07Screen()
        }
        createRoute(Destination.Tips_08.route) {
            Tips08Screen()
        }
        createRoute(Destination.Tips_09.route) {
            Tips09Screen()
        }
        createRoute(Destination.Tips_10.route) {
            Tips10Screen()
        }
        createRoute(Destination.Tips_11.route) {
            Tips11Screen()
        }
        createRoute(Destination.Tips_12.route) {
            Tips12Screen()
        }
        createRoute(Destination.Tips_13.route) {
            Tips13Screen()
        }
        createRoute(Destination.Tips_14.route) {
            Tips14Screen()
        }
        createRoute(Destination.Tips_15.route) {
            Tips15Screen()
        }
        createRoute(Destination.Tips_16.route) {
            Tips16Screen()
        }
        createRoute(Destination.Tips_17.route) {
            Tips17Screen()
        }
        createRoute(Destination.Tips_17a.route) {
            Tips17aScreen()
        }
        createRoute(Destination.Tips_18.route) {
            Tips18Screen()
        }
        createRoute(Destination.Tips_19.route) {
            Tips19Screen()
        }
        createRoute(Destination.Tips_20.route) {
            Tips20Screen()
        }
    }
}


fun NavGraphBuilder.createRoute(
    route: String, screen: @Composable () -> Unit
) {
    composable(route = route, enterTransition = {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, initialOffset = { it / 4 }) + fadeIn()
    }, exitTransition = {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, targetOffset = { it / 4 }) + fadeOut()
    }) {
        screen()
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}