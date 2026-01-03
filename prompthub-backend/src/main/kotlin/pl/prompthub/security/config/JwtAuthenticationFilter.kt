package pl.prompthub.security.config

import pl.prompthub.security.token.TokenRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val tokenRepository: TokenRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
            ?: return filterChain.doFilter(request, response)

        if (!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.removePrefix("Bearer ").trim()
        val username = jwtService.extractUsername(jwt)

        if (SecurityContextHolder.getContext().authentication != null) {
            filterChain.doFilter(request, response)
            return
        }

        val token = tokenRepository.findByToken(jwt)
            ?: return filterChain.doFilter(request, response)

        if (token.expired || token.revoked) {
            filterChain.doFilter(request, response)
            return
        }

        val userDetails = userDetailsService.loadUserByUsername(username)

        if (!jwtService.isTokenValid(jwt, userDetails)) {
            filterChain.doFilter(request, response)
            return
        }

        val authToken = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }

        SecurityContextHolder.getContext().authentication = authToken
        filterChain.doFilter(request, response)
    }
}