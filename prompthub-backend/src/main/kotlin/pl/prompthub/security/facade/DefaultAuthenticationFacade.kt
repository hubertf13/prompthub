package pl.prompthub.security.facade

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import pl.prompthub.exception.UnauthorizedOperationException
import pl.prompthub.security.user.User

@Component
class DefaultAuthenticationFacade : AuthenticationFacade {

    override val authentication: Authentication?
        get() = SecurityContextHolder.getContext().authentication

    override fun authenticatedUser(): User {
        val authentication = authentication
            ?: throw UnauthorizedOperationException("Unauthorized")

        return authentication.principal as? User
            ?: throw UnauthorizedOperationException("Invalid authentication principal")
    }
}