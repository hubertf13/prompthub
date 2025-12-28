package pl.prompthub.auth

import pl.prompthub.auth.dto.AuthResponse
import pl.prompthub.auth.dto.LoginRequest
import pl.prompthub.auth.dto.RegisterRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): AuthResponse =
        authService.register(request)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse =
        authService.login(request)
}