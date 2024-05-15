package com.example.androidReferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidReferences.ui.theme.AndroidReferencesTheme
import com.example.androidReferences.apps.loginApp.ui.auth.AuthTabs
import com.example.androidReferences.apps.loginApp.ui.home.HomeScreen
import com.example.androidReferences.apps.loginApp.ui.home.HomeViewModelImpl
import com.example.androidReferences.apps.retrofitApp.presentation.allCountries.CountriesScreen
import com.example.androidReferences.apps.retrofitApp.presentation.allCountries.CountriesViewModel
import com.example.androidReferences.apps.retrofitApp.presentation.viewModelFactory


val appScreens = mapOf(
    Screen.Auth.route to "Login Register",
    Screen.Countries.route to "Retrofit Countries"
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidReferencesTheme {
                val viewModel = viewModel<CountriesViewModel>(
                    factory = viewModelFactory {
                        CountriesViewModel(ReferenceApp.retrofitApp.countriesRepository)
                    }
                )
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (currentRoute != Screen.Parent.route) {
                            FloatingActionButton(
                                onClick = { navController.popBackStack() },
                                content = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                },
                            )
                        }
                    },
                ) { paddingValues ->
                    NavHost(navController = navController, startDestination = Screen.Parent.route) {
                        composable(route = Screen.Parent.route) {
                            ParentScreen(
                                navController = navController,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        composable(route = Screen.Countries.route) {
                            CountriesScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        composable(route = Screen.Auth.route) {
                            AuthTabs(
                                navController = navController,
                                modifier = Modifier.padding(paddingValues)
                            )
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
                                loggedUser = loggedUserParam,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }
                }
            }
        }
    }
}
