package com.example.androidReferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidReferences.ui.theme.AndroidReferencesTheme
import com.example.androidReferences.loginApp.ui.Screen
import com.example.androidReferences.loginApp.ui.auth.AuthTabs
import com.example.androidReferences.loginApp.ui.home.HomeScreen
import com.example.androidReferences.loginApp.ui.home.HomeViewModelImpl


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidReferencesTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Auth.route) {
                    composable(route = Screen.Auth.route) {
                        AuthTabs(navController = navController)
                    }

                    composable(route = "${Screen.Home.route}/{loggedUser}", arguments = listOf(
                        navArgument(name = "loggedUser") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                        val loggedUserParam = backStackEntry.arguments?.getString("loggedUser") ?: "None"
                        HomeScreen(
                            vm = HomeViewModelImpl(),
                            navController = navController,
                            loggedUser = loggedUserParam
                        )
                    }
                }
            }
        }
    }
}

