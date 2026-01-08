package pl.filipczuk.prompthub.features.post_manage.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class CreatePostViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    fun createPost(
        prompt: String,
        tag: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.createPost(prompt, tag)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}