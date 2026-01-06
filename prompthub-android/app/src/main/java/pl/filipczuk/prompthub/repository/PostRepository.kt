package pl.filipczuk.prompthub.repository

import android.content.Context
import pl.filipczuk.prompthub.api.PostApi
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.storage.TokenStorage
import pl.filipczuk.prompthub.dto.CreatePostRequest
import pl.filipczuk.prompthub.dto.PostResponse
import pl.filipczuk.prompthub.dto.UpdatePostRequest

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

    suspend fun getAllPosts(): List<PostResponse> = api.getAllPosts()

    suspend fun getMyPosts(): List<PostResponse> = api.getMyPosts()

    suspend fun deletePost(postId: Long) = api.deletePost(postId)

    suspend fun getPost(postId: Long): PostResponse = api.getPost(postId)

    suspend fun updatePost(
        postId: Long,
        request: UpdatePostRequest
    ): PostResponse {
        return api.updatePost(postId, request)
    }
}