package pl.filipczuk.prompthub.features.profile.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.core.design_system.components.PromptHubTopBar
import pl.filipczuk.prompthub.features.auth.presentation.viewmodel.AuthViewModel
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.post_manage.presentation.screen.DeletePostDialog
import pl.filipczuk.prompthub.features.profile.presentation.components.ProfilePostItem
import pl.filipczuk.prompthub.features.profile.presentation.viewmodel.ProfileViewModel
import pl.filipczuk.prompthub.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(context) }
    val viewModel = remember { ProfileViewModel(context) }

    val refreshState = rememberPullToRefreshState()
    var postToDelete by remember { mutableStateOf<PostResponse?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadMyPosts()
    }

    LaunchedEffect(viewModel.error) {
        viewModel.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.errorShown()
        }
    }

    Scaffold(
        topBar = {
            PromptHubTopBar(
                navController = navController,
                authViewModel = authViewModel
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        PullToRefreshBox(
            state = refreshState,
            isRefreshing = viewModel.isLoading,
            onRefresh = viewModel::loadMyPosts,
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
                    text = viewModel.username?.let { "Welcome to your personalized profile page $it" }
                        ?: "Welcome to your personalized profile page",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = viewModel.posts,
                        key = { it.id }
                    ) { post ->
                        ProfilePostItem(
                            post = post,
                            onEdit = {
                                navController.navigate("${Screen.EditPost.route}/${post.id}")
                            },
                            onDelete = {
                                postToDelete = post
                            }
                        )
                    }
                }
            }
        }
    }

    postToDelete?.let { post ->
        DeletePostDialog(
            onConfirm = {
                viewModel.deletePost(post.id)
                postToDelete = null
            },
            onDismiss = {
                postToDelete = null
            }
        )
    }
}