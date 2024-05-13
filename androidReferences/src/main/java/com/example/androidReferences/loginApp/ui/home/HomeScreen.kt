package com.example.androidReferences.loginApp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController


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

        Text(text = "Other Existing users:")

        LazyColumn {
            items(users.value.size) { i ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Name: ${users.value[i].name}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pass: ${users.value[i].password}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Is Admin: ${users.value[i].isAdmin}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Is Logged: ${users.value[i].isLogged}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
