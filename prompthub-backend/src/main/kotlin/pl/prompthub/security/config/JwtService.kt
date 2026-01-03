package pl.prompthub.security.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {

    fun extractUsername(token: String): String =
        extractClaim(token) { it.subject }

    fun <T> extractClaim(
        token: String,
        claimsResolver: (Claims) -> T
    ): T =
        claimsResolver(extractAllClaims(token))

    fun generateToken(
        userDetails: UserDetails,
        extraClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.expiration))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        extractUsername(token) == userDetails.username &&
                !isTokenExpired(token)

    private fun isTokenExpired(token: String): Boolean =
        extractClaim(token) { it.expiration }.before(Date())

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body

    private val signingKey: Key
        get() {
            val keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}
