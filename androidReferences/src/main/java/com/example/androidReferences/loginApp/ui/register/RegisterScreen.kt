package com.example.androidReferences.loginApp.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.androidReferences.loginApp.ui.Screen
import com.example.androidReferences.loginApp.ui.components.CustomTextBox
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(
    vm: RegisterViewModel,
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
                        navController.navigate(Screen.Login.route)
                        vm.resetFields()
                    }
                ) {
                    Text(text = "Go To Login")
                }
            }

            CustomTextBox(
                leadingIcon = listOf(Icons.Filled.Person, Icons.Outlined.Person),
                value = userName.value,
                onValueChange = vm::setUser,
                label = "Username",
                placeholder = "Register name",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextBox(
                leadingIcon = listOf(Icons.Filled.Password, Icons.Outlined.Password),
                value = pass.value,
                onValueChange = vm::setPass,
                label = "Password",
                placeholder = "Register password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                keyboardController?.hide()
                vm.register(userName.value, pass.value) { success ->
                    if (success) {
                        navController.popBackStack() // force back to the Login Screen
                        navController.navigate("${Screen.Home.route}/${userName.value}")
                        vm.resetFields()
                    } else {
                        // display snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Cannot register user ${userName.value}",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }

            }) {
                Text(text = "Register")
            }
        }
    }
}
