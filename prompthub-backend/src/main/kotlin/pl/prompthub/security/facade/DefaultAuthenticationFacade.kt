package pl.prompthub.security.facade

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class DefaultAuthenticationFacade : AuthenticationFacade {

    override val authentication: Authentication?
        get() = SecurityContextHolder.getContext().authentication
}