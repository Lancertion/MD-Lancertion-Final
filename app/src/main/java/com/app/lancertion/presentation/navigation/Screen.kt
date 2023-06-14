package com.app.lancertion.presentation.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login")
    object Register: Screen("register")
    object Guest: Screen("guest")
    object Home: Screen("home")
    object Diagnose: Screen("diagnose")
    object Community: Screen("community")
    object Survey: Screen("survey")
}