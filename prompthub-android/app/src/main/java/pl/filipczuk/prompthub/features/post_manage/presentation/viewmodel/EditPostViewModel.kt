package pl.filipczuk.prompthub.features.post_manage.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.home.data.remote.UpdatePostRequest
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class EditPostViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    var post by mutableStateOf<PostResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var isUpdating by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun fetchPost(postId: Long, onResult: (PostResponse) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            when (val result = repository.getPost(postId)) {
                is Result.Success -> {
                    post = result.data
                    onResult(result.data)
                }
                is Result.Error -> {
                    error = result.error
                }
            }
            isLoading = false
        }
    }

    fun updatePost(
        postId: Long,
        prompt: String,
        tag: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isUpdating = true
            error = null
            val postRequest = UpdatePostRequest(
                prompt = prompt,
                tag = tag
            )
            when (val result = repository.updatePost(postId, postRequest)) {
                is Result.Success -> {
                    onSuccess()
                }
                is Result.Error -> {
                    error = result.error
                }
            }
            isUpdating = false
        }
    }

    fun errorShown() {
        error = null
    }
}