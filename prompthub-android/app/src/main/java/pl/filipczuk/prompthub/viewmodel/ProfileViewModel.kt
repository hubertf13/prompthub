package pl.filipczuk.prompthub.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.dto.PostResponse
import pl.filipczuk.prompthub.repository.PostRepository

class ProfileViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    val posts = mutableStateOf<List<PostResponse>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun loadMyPosts() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                posts.value = repository.getMyPosts()
            } catch (e: Exception) {
                error.value = "Failed to load your posts"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun deletePost(
        postId: Long
    ) {
        viewModelScope.launch {
            try {
                repository.deletePost(postId)
                posts.value = posts.value.filterNot { it.id == postId }
            } catch (e: Exception) {

            }
        }
    }
}