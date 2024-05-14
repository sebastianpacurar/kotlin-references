package com.example.androidReferences.loginApp.ui

// represents different screens or destinations in the app, with specific routes
sealed class Screen(val route: String) {
    data object Auth : Screen("auth")
    data object Home : Screen("home")
}
