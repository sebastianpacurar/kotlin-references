package com.example.androidReferences.loginApp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.androidReferences.loginApp.ui.components.UserCard


@Composable
fun HomeScreen(
    vm: HomeViewModel,
    navController: NavController,
    loggedUser: String,
) {

    // set states up (bad way)
    vm.setCurrUser(loggedUser)
    val currUser = vm.currUser.collectAsStateWithLifecycle()

    vm.setUsers(currUser.value)
    val users = vm.users.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome ${currUser.value}")
        Button(
            onClick = {
                vm.logout(loggedUser)
                navController.popBackStack()
            }
        ) {
            Text(text = "Log out")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Other users (${users.value.size}):")

        LazyColumn {
            items(users.value.size) { i ->
                UserCard(
                    name = users.value[i].name,
                    pass = users.value[i].password,
                    isAdmin = users.value[i].isAdmin.toString(),
                    address = users.value[i].address,
                    number = users.value[i].number,
                )
            }
        }
    }
}
