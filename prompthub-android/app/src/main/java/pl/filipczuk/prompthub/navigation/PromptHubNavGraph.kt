package pl.filipczuk.prompthub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.filipczuk.prompthub.features.auth.presentation.screen.LoginScreen
import pl.filipczuk.prompthub.features.auth.presentation.screen.RegisterScreen
import pl.filipczuk.prompthub.features.home.presentation.screen.HomeScreen
import pl.filipczuk.prompthub.features.profile.presentation.screen.ProfileScreen
import pl.filipczuk.prompthub.features.post_manage.presentation.screen.CreatePostScreen
import pl.filipczuk.prompthub.features.post_manage.presentation.screen.EditPostScreen

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

        composable(Screen.CreatePost.route) {
            CreatePostScreen(navController)
        }

        composable(Screen.MyProfile.route) {
            ProfileScreen(navController)
        }

        composable(
            route = "${Screen.EditPost.route}/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId")
            if (postId != null) {
                EditPostScreen(navController = navController, postId = postId)
            }
        }
    }
}