package pl.prompthub.security.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)