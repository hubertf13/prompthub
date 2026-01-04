package pl.prompthub.mapper

import pl.prompthub.dto.AuthorResponse
import pl.prompthub.dto.PostResponse
import pl.prompthub.model.Post

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