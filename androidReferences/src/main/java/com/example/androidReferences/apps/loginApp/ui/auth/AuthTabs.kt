package com.example.androidReferences.apps.loginApp.ui.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.DoorFront
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.androidReferences.loginApp.ui.auth.login.LoginTab
import com.example.androidReferences.loginApp.ui.auth.login.LoginViewModelImpl
import com.example.androidReferences.apps.loginApp.ui.auth.register.RegisterTab
import com.example.androidReferences.apps.loginApp.ui.auth.register.RegisterViewModelImpl


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthTabs(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val tabItems = listOf(
        AuthItem(
            title = "Login",
            tabContent = { LoginTab(vm = LoginViewModelImpl(), navController = navController) },
            unselectedIcon = Icons.Outlined.DoorFront,
            selectedIcon = Icons.AutoMirrored.Outlined.Login
        ),
        AuthItem(
            title = "Register",
            tabContent = { RegisterTab(vm = RegisterViewModelImpl(), navController = navController) },
            unselectedIcon = Icons.Outlined.PersonAdd,
            selectedIcon = Icons.Filled.PersonAdd
        ),
    )

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }

    /*
      swipe:
       1) swipe event: updates pagerState.currentPage and pagerState.isScrollInProgress becomes true
       2) trigger 1st LE: update selectedTabIndex when swipe transition ends
       3) trigger 2nd LE: animateScrollToPage(): updates only the tab header, since the currentPage has been updated

      tap:
       1) Tab.onclick(): updates selectedTabIndex to the value of the Tab's index
       2) trigger 2nd LE: animateScrollToPage(): updates pagerState.currentPage and pagerState.isScrollInProgress
       3) trigger 1st LE: selectedTabIndex is already the same val as pagerState.currentPage. won't cause any changes
    * */

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = "Tab icon"
                        )
                    })
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { i ->
            tabItems[i].tabContent()
        }
    }
}


data class AuthItem(
    val title: String,
    val tabContent: @Composable () -> Unit,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
