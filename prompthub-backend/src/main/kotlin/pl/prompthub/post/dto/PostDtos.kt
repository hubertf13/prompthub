package pl.prompthub.post.dto

data class AuthorResponse(
    val id: Long,
    val username: String,
    val email: String
)

data class CreatePostRequest(
    val prompt: String,
    val tag: String
)

data class PostResponse(
    val id: Long,
    val prompt: String,
    val tag: String,
    val author: AuthorResponse
)

data class UpdatePostRequest(
    val prompt: String,
    val tag: String
)