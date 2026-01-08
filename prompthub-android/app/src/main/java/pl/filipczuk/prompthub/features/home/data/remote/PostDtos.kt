package pl.filipczuk.prompthub.features.home.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreatePostRequest(
    val prompt: String,
    val tag: String
)

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: Long,
    val prompt: String,
    val tag: String,
    val author: AuthorResponse
)

@JsonClass(generateAdapter = true)
data class AuthorResponse(
    val id: Long,
    val username: String,
    val email: String
)

@JsonClass(generateAdapter = true)
data class UpdatePostRequest(
    val prompt: String,
    val tag: String
)