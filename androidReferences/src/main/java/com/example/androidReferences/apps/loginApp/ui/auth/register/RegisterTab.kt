package com.example.androidReferences.apps.loginApp.ui.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.androidReferences.Screen
import com.example.androidReferences.apps.loginApp.ui.components.CustomTextBox
import com.example.androidReferences.apps.loginApp.ui.components.Dropdown
import kotlinx.coroutines.launch


@Composable
fun RegisterTab(
    vm: RegisterViewModel,
    navController: NavController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val userName = vm.user.collectAsStateWithLifecycle()
    val pass = vm.pass.collectAsStateWithLifecycle()
    val address = vm.address.collectAsStateWithLifecycle()
    val continent = vm.continent.collectAsStateWithLifecycle() // TODO: this one's not working
    val number = vm.number.collectAsStateWithLifecycle()

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
                label = "Username",
                value = userName.value,
                onValueChange = vm::setUser,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                placeholder = "Register name",
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextBox(
                label = "Password",
                value = pass.value,
                onValueChange = vm::setPass,
                selectedIcon = Icons.Filled.Lock,
                unselectedIcon = Icons.Outlined.Lock,
                placeholder = "Register password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextBox(
                label = "Address",
                value = address.value,
                onValueChange = vm::setAddress,
                selectedIcon = Icons.Filled.House,
                unselectedIcon = Icons.Outlined.House,
                placeholder = "Type address",
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextBox(
                label = "Number",
                value = number.value,
                onValueChange = vm::setNumber,
                selectedIcon = Icons.Filled.Numbers,
                unselectedIcon = Icons.Outlined.Numbers,
                placeholder = "Type Number",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Dropdown(
                label = "Continent",
                onValueChange = vm::setContinent,
                selectedIcon = Icons.Filled.AddLocationAlt,
                unselectedIcon = Icons.Outlined.AddLocationAlt,
                placeholder = "Pick a Continent",
                options = continents,
            )


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(onClick = {
                    vm.setRandomData()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Randomize user"
                    )
                }

                Button(onClick = {
                    keyboardController?.hide()
                    vm.register(
                        user = userName.value,
                        pass = pass.value,
                        address = address.value,
                        continent = continent.value,
                        number = number.value
                    ) { success ->
                        if (success) {
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
}


val continents = listOf(
    "Asia",
    "Europe",
    "Africa",
    "North America",
    "South America",
    "Australia",
    "Antarctica"
)
