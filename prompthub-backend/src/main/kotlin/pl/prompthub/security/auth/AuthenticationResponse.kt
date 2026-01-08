package pl.prompthub.security.auth

data class AuthenticationResponse(
    val token: String,
    val username: String,
)