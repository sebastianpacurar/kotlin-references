package com.example.androidReferences

// represents different screens or destinations in the app, with specific routes
sealed class Screen(val route: String) {
    data object Parent : Screen("main")
    data object Auth : Screen("auth")
    data object Home : Screen("home")
    data object Countries : Screen("countries")
}
