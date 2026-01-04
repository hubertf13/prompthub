package pl.filipczuk.prompthub.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            HeroSection()
            Spacer(Modifier.height(24.dp))
            SearchBar(
                value = viewModel.searchQuery.value,
                onValueChange = { newValue ->
                    viewModel.onSearchQueryChange(newValue)
                }
            )
            Spacer(Modifier.height(16.dp))

            when {
                viewModel.isLoading.value -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                viewModel.error.value != null -> {
                    Text(
                        text = viewModel.error.value!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 16.dp)
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