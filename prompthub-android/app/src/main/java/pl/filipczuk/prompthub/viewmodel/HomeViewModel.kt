package pl.filipczuk.prompthub.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.dto.PostResponse
import pl.filipczuk.prompthub.repository.PostRepository

class HomeViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    private val allPosts = mutableStateOf<List<PostResponse>>(emptyList())

    val posts = mutableStateOf<List<PostResponse>>(emptyList())
    val searchQuery = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun loadPosts() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                val result = repository.getAllPosts()
                allPosts.value = result
                applyFilter()
            } catch (e: Exception) {
                error.value = "Failed to load posts"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        applyFilter()
    }

    fun applyFilter() {
        val query = searchQuery.value.trim()

        posts.value =
            if (query.isEmpty()) {
                allPosts.value
            } else {
                allPosts.value.filter { post ->
                    post.tag.contains(query, ignoreCase = true) ||
                            post.author.username.contains(query, ignoreCase = true)
                }
            }
    }
}