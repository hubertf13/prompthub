package pl.prompthub.security.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service
import pl.prompthub.security.token.TokenRepository

@Service
class LogoutService(
    private val tokenRepository: TokenRepository
) : LogoutHandler {

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        val authHeader = request.getHeader("Authorization")
            ?: return

        if (!authHeader.startsWith("Bearer ")) return

        val jwt = authHeader.removePrefix("Bearer ").trim()

        val token = tokenRepository.findByToken(jwt)
            ?: return

        token.expired = true
        token.revoked = true

        tokenRepository.save(token)
    }
}