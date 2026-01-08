package pl.filipczuk.prompthub.features.home.data.repository

import android.content.Context
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.network.safeApiCall
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

    suspend fun createPost(prompt: String, tag: String): Result<Unit, String> = safeApiCall {
        api.createPost(
            CreatePostRequest(
                prompt = prompt,
                tag = tag
            )
        )
    }

    suspend fun getAllPosts(): Result<List<PostResponse>, String> = safeApiCall {
        api.getAllPosts()
    }

    suspend fun getMyPosts(): Result<List<PostResponse>, String> = safeApiCall {
        api.getMyPosts()
    }

    suspend fun deletePost(postId: Long): Result<Unit, String> = safeApiCall {
        api.deletePost(postId)
    }

    suspend fun getPost(postId: Long): Result<PostResponse, String> = safeApiCall {
        api.getPost(postId)
    }

    suspend fun updatePost(
        postId: Long,
        request: UpdatePostRequest
    ): Result<PostResponse, String> = safeApiCall {
        api.updatePost(postId, request)
    }

    suspend fun notifyPostCopied(postId: Long): Result<Unit, String> = safeApiCall {
        api.notifyPostCopied(postId)
    }
}