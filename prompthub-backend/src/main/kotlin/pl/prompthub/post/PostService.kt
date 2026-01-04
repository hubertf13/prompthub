package pl.prompthub.post

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.prompthub.post.dto.CreatePostRequest
import pl.prompthub.post.dto.PostResponse
import pl.prompthub.post.dto.UpdatePostRequest
import pl.prompthub.exception.PostNotFoundException
import pl.prompthub.exception.UnauthorizedOperationException
import pl.prompthub.post.mapper.toResponse
import pl.prompthub.post.mapper.toResponseList
import pl.prompthub.security.facade.AuthenticationFacade
import pl.prompthub.security.user.User

@Service
class PostService(
    private val postRepository: PostRepository,
    private val authenticationFacade: AuthenticationFacade
) {

    private val log = LoggerFactory.getLogger(PostService::class.java)

    fun findById(id: Long): PostResponse {
        log.info("Finding post by ID: {}", id)
        return findExistingPost(id).toResponse()
    }

    fun addPost(request: CreatePostRequest): PostResponse {
        log.info("Adding post: {}", request)

        val user = authenticatedUser()

        val post = Post(
            prompt = request.prompt,
            tag = request.tag,
            user = user
        ).apply {
            deleteHashtagsFromTag()
        }

        return postRepository.save(post).toResponse()
    }

    fun findAll(): List<PostResponse> {
        log.info("Finding all posts")
        return postRepository.findAll().toResponseList()
    }

    fun findPostsByUserId(userId: Long): List<PostResponse> {
        log.info("Finding posts by user ID: {}", userId)
        return postRepository.findAllByUserId(userId)
            .map { it.toResponse() }
    }

    fun updatePost(id: Long, updatedPost: UpdatePostRequest): PostResponse {
        val post = findExistingPost(id)
        verifyOwnership(post)

        post.prompt = updatedPost.prompt
        post.tag = updatedPost.tag
        post.deleteHashtagsFromTag()

        log.info("Updating post with ID: {}", id)
        return postRepository.save(post).toResponse()
    }

    fun deleteById(id: Long) {
        val post = findExistingPost(id)
        verifyOwnership(post)

        log.info("Deleting post by ID: {}", id)
        postRepository.deleteById(id)
    }

    private fun findExistingPost(id: Long): Post =
        postRepository.findPostById(id)
            ?: throw PostNotFoundException("Post not found with id: $id")

    private fun authenticatedUser(): User {
        val authentication = authenticationFacade.authentication
            ?: throw UnauthorizedOperationException("Unauthorized")

        return authentication.principal as? User
            ?: throw UnauthorizedOperationException("Invalid authentication principal")
    }

    private fun verifyOwnership(post: Post) {
        val authenticatedEmail = authenticationFacade.authentication?.name
            ?: throw UnauthorizedOperationException("Unauthorized")

        val ownerEmail = post.user.email

        if (authenticatedEmail != ownerEmail) {
            log.warn(
                "Post owner email: {}, attempted operation by: {}",
                ownerEmail,
                authenticatedEmail
            )
            throw UnauthorizedOperationException(
                "You are not the owner of this post"
            )
        }
    }
}
