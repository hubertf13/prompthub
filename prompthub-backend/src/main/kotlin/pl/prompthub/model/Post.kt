package pl.prompthub.model

import pl.prompthub.security.user.User
import jakarta.persistence.*

@Entity
class Post(

    @Id
    @GeneratedValue
    val id: Long,
    var prompt: String,
    var tag: String,

    @ManyToOne
    var user: User
) {

    fun deleteHashtagsFromTag() {
        tag = tag.replace("#", "")
    }
}