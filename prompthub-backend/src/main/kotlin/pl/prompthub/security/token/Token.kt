package pl.prompthub.security.token

import pl.prompthub.security.user.User
import jakarta.persistence.*

@Entity
class Token(

    @Id
    @GeneratedValue
    val id: Long? = null,
    var token: String,

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType,
    var expired: Boolean = false,
    var revoked: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)
