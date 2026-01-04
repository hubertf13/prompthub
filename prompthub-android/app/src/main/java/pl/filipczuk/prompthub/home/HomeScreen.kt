package pl.filipczuk.prompthub.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.auth.AuthViewModel

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val authViewModel = remember {
        AuthViewModel(context)
    }

    Scaffold(
        topBar = {
            PromptHubTopBar(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            HeroSection()
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar()
            PostsPlaceholder()
        }
    }
}
