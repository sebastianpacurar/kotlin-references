package com.example.androidReferences.loginApp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.example.androidReferences.loginApp.ui.Screen
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    vm: LoginViewModel,
    navController: NavController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val userName = vm.user.collectAsStateWithLifecycle()
    val pass = vm.pass.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = padding.calculateTopPadding(), vertical = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Row(
                verticalAlignment = Alignment.Top
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.Register.route)
                        vm.resetFields()
                    }
                ) {
                    Text(text = "Go To Register")
                }
            }

            OutlinedTextField(
                value = userName.value,
                onValueChange = vm::setUser,
                label = {
                    Text(text = "Username")
                },
                placeholder = {
                    Text(text = "Insert user name")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pass.value,
                onValueChange = vm::setPass,
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Insert user password")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                keyboardController?.hide()
                vm.login(userName.value, pass.value) { success ->
                    if (success) {
                        //TODO: navArgs are broken
                        navController.navigate("${Screen.Home.route}/loggedUser=${userName.value}")
                    } else {
                        // display snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Username or password invalid",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
                vm.resetFields()
            }) {
                Text(text = "Login")
            }
        }
    }
}
