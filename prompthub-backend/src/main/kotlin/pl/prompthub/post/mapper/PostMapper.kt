package pl.prompthub.post.mapper

import pl.prompthub.post.dto.AuthorResponse
import pl.prompthub.post.dto.PostResponse
import pl.prompthub.post.Post

fun Post.toResponse(): PostResponse =
    PostResponse(
        id = requireNotNull(id),
        prompt = prompt,
        tag = tag,
        author = AuthorResponse(
            id = requireNotNull(user.id),
            username = user.getActualUsername()
        )
    )

fun List<Post>.toResponseList(): List<PostResponse> = map { it.toResponse() }