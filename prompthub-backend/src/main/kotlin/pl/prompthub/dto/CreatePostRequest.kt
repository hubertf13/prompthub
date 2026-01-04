package pl.prompthub.dto

data class CreatePostRequest(
    val prompt: String,
    val tag: String
)