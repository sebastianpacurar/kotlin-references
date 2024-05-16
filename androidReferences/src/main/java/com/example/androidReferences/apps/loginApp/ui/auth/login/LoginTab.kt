package com.example.androidReferences.loginApp.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.example.androidReferences.Screen
import com.example.androidReferences.ui.components.CustomTextBox
import kotlinx.coroutines.launch


@Composable
fun LoginTab(
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
                .padding(top = padding.calculateTopPadding() + 35.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTextBox(
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                value = userName.value,
                onValueChange = vm::setUser,
                label = "Username",
                placeholder = "Insert user name",
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextBox(
                selectedIcon = Icons.Filled.Lock,
                unselectedIcon = Icons.Outlined.Lock,
                value = pass.value,
                onValueChange = vm::setPass,
                label = "Password",
                placeholder = "Insert user password",
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    vm.login(userName.value, pass.value) { success ->
                        if (success) {
                            navController.navigate("${Screen.Home.route}/${userName.value}")
                            vm.resetFields()
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
                }) {
                Text(text = "Login")
            }
        }
    }
}
