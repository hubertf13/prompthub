package pl.filipczuk.prompthub.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.filipczuk.prompthub.auth.AuthViewModel
import pl.filipczuk.prompthub.ui.home.PostItem
import pl.filipczuk.prompthub.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val authViewModel = remember {
        AuthViewModel(context)
    }

    val viewModel = remember {
        HomeViewModel(context)
    }

    val refreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
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
                viewModel.loadPosts()
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
                HeroSection()
                Spacer(Modifier.height(24.dp))
                SearchBar(
                    value = viewModel.searchQuery.value,
                    onValueChange = viewModel::onSearchQueryChange
                )
                Spacer(Modifier.height(16.dp))

                when {
                    viewModel.isLoading.value && viewModel.posts.value.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    viewModel.error.value != null -> {
                        Text(
                            text = viewModel.error.value!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(viewModel.posts.value) { post ->
                                PostItem(
                                    post = post,
                                    onTagClick = { tag ->
                                        viewModel.onSearchQueryChange(tag)
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