package pl.prompthub.auth.dto

data class LoginRequest(
    val username: String,
    val password: String
)