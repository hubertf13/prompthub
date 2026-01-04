package pl.filipczuk.prompthub.api

import pl.filipczuk.prompthub.dto.CreatePostRequest
import pl.filipczuk.prompthub.dto.PostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PostApi {

    @POST("post/add")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): PostResponse
}