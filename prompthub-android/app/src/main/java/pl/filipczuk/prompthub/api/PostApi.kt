package pl.filipczuk.prompthub.api

import pl.filipczuk.prompthub.dto.CreatePostRequest
import pl.filipczuk.prompthub.dto.PostResponse
import pl.filipczuk.prompthub.dto.UpdatePostRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {

    @POST("post/add")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): PostResponse

    @GET("post/all")
    suspend fun getAllPosts(): List<PostResponse>

    @GET("post/all/user/me")
    suspend fun getMyPosts(): List<PostResponse>

    @DELETE("post/delete/{id}")
    suspend fun deletePost(
        @Path("id") postId: Long
    )

    @GET("post/{id}")
    suspend fun getPost(
        @Path("id") postId: Long
    ): PostResponse

    @PATCH("post/update/{id}")
    suspend fun updatePost(
        @Path("id") postId: Long,
        @Body request: UpdatePostRequest
    ): PostResponse

    @POST("post/copy/{id}")
    suspend fun notifyPostCopied(@Path("id") postId: Long)
}