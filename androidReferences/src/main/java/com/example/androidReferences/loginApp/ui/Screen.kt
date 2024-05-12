package com.example.androidReferences.loginApp.ui

// represents different screens or destinations in the app, with specific routes
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
}
