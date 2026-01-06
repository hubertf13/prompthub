package pl.filipczuk.prompthub.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.auth.AuthViewModel
import pl.filipczuk.prompthub.home.PromptHubTopBar
import pl.filipczuk.prompthub.ui.profile.ProfilePostItem
import pl.filipczuk.prompthub.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val authViewModel = remember {
        AuthViewModel(context)
    }

    val viewModel = remember {
        ProfileViewModel(context)
    }

    val refreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.loadMyPosts()
    }

    Scaffold(
        topBar = {
            PromptHubTopBar(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    ) { padding ->

        PullToRefreshBox(
            state = refreshState,
            isRefreshing = viewModel.isLoading.value,
            onRefresh = {
                viewModel.loadMyPosts()
            },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = "My Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Welcome to your personalized profile page",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(24.dp))

                when {
                    viewModel.isLoading.value -> {
                        CircularProgressIndicator()
                    }

                    viewModel.error.value != null -> {
                        Text(
                            text = viewModel.error.value!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        LazyColumn {
                            items(viewModel.posts.value) { post ->
                                ProfilePostItem(
                                    post = post,
                                    onEdit = {
                                        // navController.navigate("edit/${post.id}")
                                    },
                                    onDelete = {
                                        // TODO delete
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