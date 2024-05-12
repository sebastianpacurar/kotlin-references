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
import com.example.androidReferences.loginApp.ui.home.HomeScreen
import com.example.androidReferences.loginApp.ui.login.LoginScreen
import com.example.androidReferences.loginApp.ui.login.LoginViewModelImpl
import com.example.androidReferences.loginApp.ui.register.RegisterScreen
import com.example.androidReferences.loginApp.ui.register.RegisterViewModelImpl


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidReferencesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable(route = Screen.Login.route) {
                        LoginScreen(
                            vm = LoginViewModelImpl(),
                            navController = navController
                        )
                    }

                    composable(Screen.Register.route) {
                        RegisterScreen(
                            vm = RegisterViewModelImpl(),
                            navController = navController
                        )
                    }

                    composable(route = "${Screen.Home.route}/{loggedUser}", arguments = listOf(
                        navArgument(name = "loggedUser") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                        val argVal = backStackEntry.arguments?.getString("loggedUser") ?: "None"
                        HomeScreen(
                            navController = navController,
                            loggedUser = argVal
                        )
                    }
                }
            }
        }
    }
}

