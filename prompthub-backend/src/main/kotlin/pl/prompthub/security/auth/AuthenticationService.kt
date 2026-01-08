package pl.prompthub.security.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.prompthub.exception.EmailAlreadyExistsException
import pl.prompthub.exception.TokenIsNotValidException
import pl.prompthub.exception.UserNotFoundException
import pl.prompthub.exception.UsernameAlreadyExistsException
import pl.prompthub.security.config.JwtService
import pl.prompthub.security.token.Token
import pl.prompthub.security.token.TokenRepository
import pl.prompthub.security.token.TokenType
import pl.prompthub.security.user.Role
import pl.prompthub.security.user.User
import pl.prompthub.security.user.UserRepository

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): AuthenticationResponse {
        validateEmailAndUsernameNotExists(request.email, request.username)
        validateEmailNotExists(request.email)
        validateUsernameNotExists(request.username)

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        val savedUser = userRepository.save(user)

        val jwtToken = jwtService.generateToken(user)

        saveUserToken(savedUser, jwtToken)

        return AuthenticationResponse(token = jwtToken, username = savedUser.getActualUsername())
    }

    private fun revokeAllUserTokens(user: User) {
        val userId = user.id
            ?: throw IllegalStateException("User must be persisted before revoking tokens")

        val validUserTokens = tokenRepository.findAllValidTokensByUser(userId)

        if (validUserTokens.isEmpty()) return

        validUserTokens.forEach { token ->
            token.expired = true
            token.revoked = true
        }

        tokenRepository.saveAll(validUserTokens)
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            user = user,
            token = jwtToken,
            tokenType = TokenType.BEARER,
            revoked = false,
            expired = false
        )
        tokenRepository.save(token)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException("User not found")

        val jwtToken = jwtService.generateToken(user)

        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)

        return AuthenticationResponse(token = jwtToken, username = user.getActualUsername())
    }

    private fun validateEmailAndUsernameNotExists(email: String, username: String) =
        listOf(
            userRepository.findByEmail(email),
            userRepository.findByUsername(username)
        ).takeIf { it.all { user -> user != null } }
            ?.let {
                throw UsernameAlreadyExistsException("Username and email already exists")
            }

    private fun validateUsernameNotExists(username: String) =
        userRepository.findByUsername(username)?.let {
            throw UsernameAlreadyExistsException("Username already exists")
        }

    private fun validateEmailNotExists(email: String) =
        userRepository.findByEmail(email)?.let {
            throw EmailAlreadyExistsException("Email already exists")
        }

    fun getUserIdFromToken(jwt: String): Long {
        val token = tokenRepository.findByToken(jwt)
            ?: throw TokenIsNotValidException("Unauthorized")

        if (token.expired || token.revoked) {
            throw TokenIsNotValidException("Unauthorized")
        }

        return requireNotNull(token.user.id) {
            "User must be persisted"
        }
    }
}