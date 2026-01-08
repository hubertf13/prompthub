package pl.filipczuk.prompthub.features.profile.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.features.auth.data.repository.AuthRepository
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class ProfileViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)
    private val authRepository = AuthRepository(context)

    var posts by mutableStateOf<List<PostResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var username by mutableStateOf<String?>(null)
        private set

    init {
        getUsername()
    }

    private fun getUsername() {
        username = authRepository.getUsername()
    }

    fun loadMyPosts() {
        viewModelScope.launch {
            isLoading = true
            error = null

            when (val result = repository.getMyPosts()) {
                is Result.Success -> {
                    posts = result.data
                }
                is Result.Error -> {
                    error = result.error
                }
            }
            isLoading = false
        }
    }

    fun deletePost(
        postId: Long
    ) {
        viewModelScope.launch {
            when (val result = repository.deletePost(postId)) {
                is Result.Success -> {
                    posts = posts.filterNot { it.id == postId }
                }
                is Result.Error -> {
                    error = result.error
                }
            }
        }
    }

    fun errorShown() {
        error = null
    }
}