package pl.prompthub.post

import pl.prompthub.security.user.User
import jakarta.persistence.*

@Entity
class Post(

    @Id
    @GeneratedValue
    val id: Long? = null,
    var prompt: String,
    var tag: String,

    @ManyToOne
    var user: User
) {

    fun deleteHashtagsFromTag() {
        tag = tag.replace("#", "")
    }
}