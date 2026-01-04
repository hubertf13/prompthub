package pl.prompthub.dto

data class PostResponse(
    val id: Long,
    val prompt: String,
    val tag: String,
    val author: AuthorResponse
)