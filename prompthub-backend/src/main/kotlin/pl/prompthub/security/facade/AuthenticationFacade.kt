package pl.prompthub.security.facade

import org.springframework.security.core.Authentication
import pl.prompthub.security.user.User

interface AuthenticationFacade {
    val authentication: Authentication?

    fun authenticatedUser(): User
}