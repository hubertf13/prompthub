package pl.prompthub.security.user

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import pl.prompthub.post.Post
import pl.prompthub.security.token.Token
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "application_user")
class User(

    @Id
    @GeneratedValue
    val id: Long? = null,
    private var username: String,
    var email: String,

    @JsonIgnore
    private var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    var tokens: MutableList<Token> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    var posts: MutableList<Post> = mutableListOf(),

    var fcmToken: String? = null

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = password

    @JsonGetter("username")
    fun getActualUsername(): String = username

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun toString(): String =
        "User(id=$id, username='$username', email='$email', role=$role, posts=${posts.size})"
}
