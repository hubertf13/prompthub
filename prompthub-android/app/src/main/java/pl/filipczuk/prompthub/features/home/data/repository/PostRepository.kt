package pl.filipczuk.prompthub.features.home.data.repository

import android.content.Context
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.storage.AuthDataStorage
import pl.filipczuk.prompthub.features.home.data.remote.CreatePostRequest
import pl.filipczuk.prompthub.features.home.data.remote.PostApi
import pl.filipczuk.prompthub.features.home.data.remote.PostResponse
import pl.filipczuk.prompthub.features.home.data.remote.UpdatePostRequest

class PostRepository(
    context: Context
) {

    private val authDataStorage = AuthDataStorage(context)
    private val api = RetrofitProvider(authDataStorage)
        .retrofit
        .create(PostApi::class.java)

    suspend fun createPost(prompt: String, tag: String) =
        api.createPost(
            CreatePostRequest(
                prompt = prompt,
                tag = tag
            )
        )

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

    suspend fun notifyPostCopied(postId: Long) = api.notifyPostCopied(postId)
}