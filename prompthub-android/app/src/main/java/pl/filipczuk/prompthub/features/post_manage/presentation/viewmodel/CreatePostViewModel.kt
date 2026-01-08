package pl.filipczuk.prompthub.features.post_manage.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.features.home.data.repository.PostRepository

class CreatePostViewModel(
    context: Context
) : ViewModel() {

    private val repository = PostRepository(context)

    var error by mutableStateOf<String?>(null)
        private set

    fun createPost(
        prompt: String,
        tag: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            error = null
            when (val result = repository.createPost(prompt, tag)) {
                is Result.Success -> {
                    onSuccess()
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