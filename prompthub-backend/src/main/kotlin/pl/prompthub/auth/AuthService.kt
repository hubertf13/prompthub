package pl.prompthub.auth

import pl.prompthub.auth.dto.AuthResponse
import pl.prompthub.auth.dto.LoginRequest
import pl.prompthub.auth.dto.RegisterRequest
import pl.prompthub.security.JwtService
import pl.prompthub.user.Role
import pl.prompthub.user.User
import pl.prompthub.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw RuntimeException("Username already exists")
        }

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        userRepository.save(user)

        val userDetails = org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf()
        )

        val token = jwtService.generateToken(userDetails, user.role.name)
        return AuthResponse(token)
    }

    fun login(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val user = userRepository.findByUsername(request.username)
            ?: throw RuntimeException("User not found")

        val userDetails = org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf()
        )

        val token = jwtService.generateToken(userDetails, user.role.name)
        return AuthResponse(token)
    }
}