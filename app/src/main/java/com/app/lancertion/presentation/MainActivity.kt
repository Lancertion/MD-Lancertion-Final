package com.app.lancertion.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.lancertion.presentation.component.BottomBar
import com.app.lancertion.presentation.component.TopProfile
import com.app.lancertion.presentation.navigation.Screen
import com.app.lancertion.presentation.screen.community.CommunityGuestScreen
import com.app.lancertion.presentation.screen.community.CommunityScreen
import com.app.lancertion.presentation.screen.community.CommunityViewModel
import com.app.lancertion.presentation.screen.community_detail.CommunityDetailScreen
import com.app.lancertion.presentation.screen.community_detail.CommunityDetailViewModel
import com.app.lancertion.presentation.screen.diagnose.DiagnoseScreen
import com.app.lancertion.presentation.screen.diagnose.DiagnoseViewModel
import com.app.lancertion.presentation.screen.guest.GuestScreen
import com.app.lancertion.presentation.screen.guest.GuestViewModel
import com.app.lancertion.presentation.screen.home.HomeScreen
import com.app.lancertion.presentation.screen.home.HomeViewModel
import com.app.lancertion.presentation.screen.login.LoginScreen
import com.app.lancertion.presentation.screen.login.LoginViewModel
import com.app.lancertion.presentation.screen.register.RegisterScreen
import com.app.lancertion.presentation.screen.register.RegisterViewModel
import com.app.lancertion.presentation.screen.survey.SurveyScreen
import com.app.lancertion.presentation.screen.survey.SurveyViewModel
import com.app.lancertion.presentation.ui.theme.LancertionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LancertionTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route


                    val viewModel: MainViewModel by viewModels()
                    val communityViewModel: CommunityViewModel by viewModels()
                    val communityDetailViewModel: CommunityDetailViewModel by viewModels()

                    var startDestination by remember { mutableStateOf(Screen.Login.route) }
                    val userId by viewModel.userId.collectAsState()

                    Scaffold(
                        topBar = {
                            when(currentRoute) {
                                Screen.Diagnose.route, Screen.Home.route -> TopProfile(
                                    name = viewModel.name.value,
                                    userId = userId,
                                    onLogout = {
                                        viewModel.logout()
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                        viewModel.refreshToken()
                                        communityViewModel.getToken()
                                        communityViewModel.getId()
                                        communityDetailViewModel.getUserId()
                                        communityDetailViewModel.getToken()
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            when(currentRoute) {
                                Screen.Home.route, Screen.Diagnose.route, Screen.Community.route -> BottomBar(navController = navController)
                            }
                        }
                    ) {innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (viewModel.token.value != "") {
                                startDestination = Screen.Home.route
                                startDestination
                            } else {
                                startDestination = Screen.Login.route
                                startDestination
                            },
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Login.route) {
                                val vm: LoginViewModel by viewModels()
                                LoginScreen(
                                    viewModel = vm,
                                    navigateToRegister = {
                                        navController.navigate(Screen.Register.route) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    navigateToGuest = {
                                        navController.navigate(Screen.Guest.route)
                                    },
                                    onLogin = {
                                        startDestination = Screen.Home.route
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                        viewModel.refreshToken()
                                        communityViewModel.getToken()
                                        communityViewModel.getId()
                                        communityDetailViewModel.getUserId()
                                        communityDetailViewModel.getToken()
                                    }
                                )
                            }

                            composable(Screen.Register.route) {
                                val vm: RegisterViewModel by viewModels()
                                RegisterScreen(
                                    viewModel = vm,
                                    navigateToLogin = {
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    navigateToGuest = {
                                        navController.navigate(Screen.Guest.route)
                                    }
                                )
                            }

                            composable(Screen.Guest.route) {
                                val vm: GuestViewModel by viewModels()
                                GuestScreen(
                                    viewModel = vm,
                                    navigateToHome = {
                                        startDestination = Screen.Home.route
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                        viewModel.refreshToken()
                                        communityViewModel.getToken()
                                        communityDetailViewModel.getToken()
                                    }
                                )
                            }

                            composable(Screen.Home.route) {
                                val vm: HomeViewModel by viewModels()
                                HomeScreen(
                                    viewModel = vm,
//                                    onDiagnose = {
//                                        navController.navigate(Screen.Diagnose.route) {
//                                            popUpTo(navController.graph.findStartDestination().id) {
//                                                saveState = true
//                                            }
//                                            restoreState = true
//                                            launchSingleTop = true
//                                        }
//                                    }
                                    onDiagnose = {
                                        navController.navigate(Screen.Survey.route){
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            restoreState = true
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            composable(Screen.Diagnose.route) {
                                val vm: DiagnoseViewModel by viewModels()
                                DiagnoseScreen(
                                    viewModel = vm
                                )
                            }

                            composable(Screen.Community.route) {
                                if(viewModel.token.value == "guest_token") {
                                    CommunityGuestScreen()
                                } else {
                                    communityViewModel.getPosts()
                                    CommunityScreen(
                                        viewModel = communityViewModel,
                                        navigateToDetail = { post ->
                                           navController.navigate(
                                               "${Screen.DetailCommunity.route}/${post.post_id}"
                                           )
                                        }
                                    )
                                }
                            }

                            composable(
                                Screen.DetailCommunityId.route,
                                arguments = listOf(
                                    navArgument("postId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                val postId = it.arguments?.getInt("postId")
                                postId?.let { id -> communityDetailViewModel.getPostId(id) }

                                CommunityDetailScreen(
                                    viewModel = communityDetailViewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    }
                                )
                            }

                            composable(Screen.Survey.route) {
                                val vm: SurveyViewModel by viewModels()
                                SurveyScreen(
                                    viewModel = vm,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToResult = {
                                        navController.navigate(Screen.Diagnose.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            restoreState = true
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LancertionTheme {
        Greeting("Android")
    }
}