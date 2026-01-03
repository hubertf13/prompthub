package pl.prompthub.security.auth

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)