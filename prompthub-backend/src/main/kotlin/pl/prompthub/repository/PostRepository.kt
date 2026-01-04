package pl.prompthub.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.prompthub.model.Post

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    fun findPostById(id: Long): Post?

    fun findAllByUserId(userId: Long): List<Post>
}