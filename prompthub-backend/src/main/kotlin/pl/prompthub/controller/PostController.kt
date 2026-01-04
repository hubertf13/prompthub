package pl.prompthub.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.prompthub.dto.CreatePostRequest
import pl.prompthub.dto.PostResponse
import pl.prompthub.dto.UpdatePostRequest
import pl.prompthub.service.PostService

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PostResponse> =
        ResponseEntity.ok(postService.findById(id))

    @GetMapping("/all")
    fun findAll(): ResponseEntity<List<PostResponse>> =
        ResponseEntity.ok(postService.findAll())

    @GetMapping("/all/user/{id}")
    fun findPostsByUserId(@PathVariable id: Long): ResponseEntity<List<PostResponse>> =
        ResponseEntity.ok(postService.findPostsByUserId(id))

    @PostMapping("/add")
    fun addPost(@RequestBody request: CreatePostRequest): ResponseEntity<PostResponse> =
        ResponseEntity.ok(postService.addPost(request))

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        postService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/update/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody updatedPost: UpdatePostRequest
    ): ResponseEntity<PostResponse> =
        ResponseEntity.ok(postService.updatePost(id, updatedPost))
}
