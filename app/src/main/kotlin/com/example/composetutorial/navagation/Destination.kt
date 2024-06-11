package com.example.composetutorial.navagation

sealed class Destination(val route: String) {
    data object Main : Destination("main")
    data object Home : Destination("home")
    data object Follow : Destination("follow")
    data object Settings : Destination("settings")
    data object MyProfile : Destination("my_user_info")

    data object Tips_01 : Destination("tips_01")
    data object Tips_02 : Destination("tips_02")
    data object Tips_03 : Destination("tips_03")
    data object Tips_04 : Destination("tips_04")
    data object Tips_05 : Destination("tips_05")
    data object Tips_05a : Destination("tips_05a")
    data object Tips_06 : Destination("tips_06")
    data object Tips_07 : Destination("tips_07")
    data object Tips_08 : Destination("tips_08")
    data object Tips_09 : Destination("tips_09")
    data object Tips_10 : Destination("tips_10")
    data object Tips_11 : Destination("tips_11")
    data object Tips_12 : Destination("tips_12")
    data object Tips_13 : Destination("tips_13")
    data object Tips_14 : Destination("tips_14")
    data object Tips_15 : Destination("tips_15")
    data object Tips_16 : Destination("tips_16")
    data object Tips_17 : Destination("tips_17")
    data object Tips_17a : Destination("tips_17a/{name}")
    data object Tips_18 : Destination("tips_18")
    data object Tips_19 : Destination("tips_19")
    data object Tips_20 : Destination("tips_20")
    data object Tips_21 : Destination("tips_21")
    data object Tips_22 : Destination("tips_22")
    data object Tips_23 : Destination("tips_23")
    data object Tips_24 : Destination("tips_24")
}