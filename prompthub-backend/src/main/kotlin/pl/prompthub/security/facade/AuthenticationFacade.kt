package pl.prompthub.security.facade

import org.springframework.security.core.Authentication

interface AuthenticationFacade {
    val authentication: Authentication?
}