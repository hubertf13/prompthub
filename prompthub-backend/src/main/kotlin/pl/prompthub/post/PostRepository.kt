package pl.prompthub.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    fun findPostById(id: Long): Post?

    fun findAllByUserId(userId: Long): List<Post>
}