package pl.prompthub.dto

data class UpdatePostRequest(
    val prompt: String,
    val tag: String
)