package pl.filipczuk.prompthub.repository

import android.content.Context
import pl.filipczuk.prompthub.api.PostApi
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.storage.TokenStorage
import pl.filipczuk.prompthub.dto.CreatePostRequest
import pl.filipczuk.prompthub.dto.PostResponse

class PostRepository(
    context: Context
) {

    private val tokenStorage = TokenStorage(context)
    private val api = RetrofitProvider(tokenStorage)
        .retrofit
        .create(PostApi::class.java)

    suspend fun createPost(prompt: String, tag: String) {
        api.createPost(
            CreatePostRequest(
                prompt = prompt,
                tag = tag
            )
        )
    }

    suspend fun getAllPosts(): List<PostResponse> {
        return api.getAllPosts()
    }
}