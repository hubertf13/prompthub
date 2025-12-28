package pl.prompthub.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank val username: String,
    @field:Email val email: String,
    @field:NotBlank val password: String
)