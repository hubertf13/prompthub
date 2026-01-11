package pl.filipczuk.prompthub.features.home.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class HomeViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    private var allPosts by mutableStateOf<List<PostResponse>>(emptyList())

    var searchQuery by mutableStateOf("")

    val posts by derivedStateOf {
        val query = searchQuery.trim()
        if (query.isEmpty()) {
            allPosts
        } else {
            allPosts.filter { post ->
                post.tag.contains(query, ignoreCase = true) ||
                        post.author.username.contains(query, ignoreCase = true)
            }
        }
    }

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadPosts() {
        viewModelScope.launch {
            isLoading = true
            error = null

            when (val result = repository.getAllPosts()) {
                is Result.Success -> {
                    allPosts = result.data
                }
                is Result.Error -> {
                    error = result.error
                }
            }
            isLoading = false
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    fun notifyPostCopied(postId: Long) =
        viewModelScope.launch {
            repository.notifyPostCopied(postId)
        }

    fun errorShown() {
        error = null
    }
}