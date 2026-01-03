package pl.prompthub.security.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.register(request))

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(authenticationService.authenticate(request))

    @GetMapping("/user")
    fun getUserIdFromToken(
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<Map<String, Long>> =
        ResponseEntity.ok(
            mapOf(
                "userId" to authenticationService.getUserIdFromToken(jwt.substring(7))
            )
        )
}