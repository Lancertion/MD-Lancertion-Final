package com.app.lancertion.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.app.lancertion.R
import com.app.lancertion.presentation.navigation.NavigationItem
import com.app.lancertion.presentation.navigation.Screen
import com.app.lancertion.presentation.ui.theme.LancertionTheme

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val items = listOf(
        NavigationItem(
            title = "Home",
            icon = painterResource(id = R.drawable.ic_home_24),
            iconActive = painterResource(id = R.drawable.ic_home_filled_24),
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Diagnose",
            icon = painterResource(id = R.drawable.ic_detail_24),
            iconActive = painterResource(id = R.drawable.ic_detail_filled_24),
            screen = Screen.Diagnose
        ),
        NavigationItem(
            title = "Community",
            icon = painterResource(id = R.drawable.ic_user_24),
            iconActive = painterResource(id = R.drawable.ic_user_filled_24),
            screen = Screen.Community
        )
    )

    NavigationBar {
        items.forEach {item ->
            NavigationBarItem(
                selected = item.screen.route == currentRoute,
                icon = {
                    Icon(
                        painter = if(item.screen.route != currentRoute) item.icon else item.iconActive,
                        contentDescription = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = Color.White
                ),
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                })
        }
    }
}

@Preview
@Composable
fun Preview() {
    LancertionTheme {
        BottomBar(navController = NavHostController(LocalContext.current))
    }
}