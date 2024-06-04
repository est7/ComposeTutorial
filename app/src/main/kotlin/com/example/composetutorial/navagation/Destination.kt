package com.example.composetutorial.navagation

sealed class Destination(val route: String) {
    data object Main : Destination("main")
    data object Home : Destination("home")
    data object Follow : Destination("follow")
    data object Settings : Destination("settings")
    data object MyProfile : Destination("my_user_info")
}