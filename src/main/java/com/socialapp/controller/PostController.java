package com.socialapp.controller;

import com.socialapp.model.Post;
import com.socialapp.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // API ENDPOINTS

    /**
     * Retrieves all posts created by a specific author.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/posts/author/{authorId}}
     *
     * @param authorId The ID of the author.
     * @return A list of posts by the specified author.
     */
    @GetMapping("/posts/author/{authorId}")
    public List<Post> getPostsByAuthor(@PathVariable String authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    /**
     * Creates a new post.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/posts}
     *
     * @param post The post object from the request body.
     * @return The saved {@link Post} object.
     */
    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    /**
     * Retrieves all posts that a specific user has liked.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/posts/liked/{userId}}
     *
     * @param userId The ID of the user whose liked posts are to be retrieved.
     * @return A list of posts liked by the user.
     */
    @GetMapping("/posts/liked/{userId}")
    public List<Post> getLikedPosts(@PathVariable String userId) {
        return postRepository.findByLikesContaining(userId);
    }
}
