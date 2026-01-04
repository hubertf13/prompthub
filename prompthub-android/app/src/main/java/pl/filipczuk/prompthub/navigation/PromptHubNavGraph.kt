package pl.filipczuk.prompthub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.filipczuk.prompthub.auth.LoginScreen
import pl.filipczuk.prompthub.auth.RegisterScreen
import pl.filipczuk.prompthub.home.HomeScreen

@Composable
fun PromptHubNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

    }
}