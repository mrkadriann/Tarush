package com.example.tarush;

sealed class ScreenNavigation(val Route: String) {

    sealed class Screen(val route: String) {
        object Login : Screen("login")
        object Home : Screen("home")
    }
}
