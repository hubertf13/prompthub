package pl.filipczuk.prompthub.features.home.presentation.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.design_system.components.PromptHubTopBar
import pl.filipczuk.prompthub.core.design_system.components.SearchBar
import pl.filipczuk.prompthub.features.auth.presentation.viewmodel.AuthViewModel
import pl.filipczuk.prompthub.features.home.presentation.components.PostItem
import pl.filipczuk.prompthub.features.home.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(context) }
    val viewModel = remember { HomeViewModel(context) }

    val refreshState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Notifications disabled. You won't be notified about post interactions."
                    )
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        viewModel.loadPosts()
    }

    LaunchedEffect(viewModel.error) {
        viewModel.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.errorShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            PromptHubTopBar(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    ) { padding ->

        PullToRefreshBox(
            state = refreshState,
            isRefreshing = viewModel.isLoading,
            onRefresh = viewModel::loadPosts,
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
                    value = viewModel.searchQuery,
                    onValueChange = viewModel::onSearchQueryChange
                )
                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = viewModel.posts,
                        key = { it.id }
                    ) { post ->
                        PostItem(
                            post = post,
                            viewModel = viewModel,
                            onTagClick = viewModel::onSearchQueryChange
                        )
                    }
                }
            }
        }
    }
}