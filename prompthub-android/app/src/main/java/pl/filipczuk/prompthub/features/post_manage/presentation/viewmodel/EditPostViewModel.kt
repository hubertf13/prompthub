package pl.filipczuk.prompthub.features.post_manage.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.home.data.remote.UpdatePostRequest
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class EditPostViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    fun fetchPost(postId: Long, onSuccess: (PostResponse) -> Unit) {
        viewModelScope.launch {
            try {
                val post = repository.getPost(postId)
                onSuccess(post)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updatePost(
        postId: Long,
        prompt: String,
        tag: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val postRequest = UpdatePostRequest(
                    prompt = prompt,
                    tag = tag
                )
                repository.updatePost(postId, postRequest)
                onSuccess()
            } catch (e: Exception) {
                onError()
            }
        }
    }
}