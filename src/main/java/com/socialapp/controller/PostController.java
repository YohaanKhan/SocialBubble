package com.socialapp.controller;

import com.socialapp.model.Post;
import com.socialapp.model.Post.Comment;
import com.socialapp.repository.PostRepository;
import com.socialapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing post-related operations.
 * <p>
 * This controller provides public endpoints for creating, retrieving,
 * liking and commenting on user posts.
 */
@RestController
@RequestMapping("/api/test")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
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

    /**
     * It allows user to like a post.
     * @param postId The Id of the post to be liked.
     * @param userId The Id of the user who is liking the post.
     * @return The updated Post object.
     */
    @PostMapping("/like")
    public Post addLike(@RequestParam String postId, @RequestParam String userId){
        return postService.addLike(postId, userId);
    }

    /**
     * It allows user to comment on a post.
     * @param postId The ID of the post to comment on.
     * @param comment The Comment object containing the comment text and author.
     * @return The updated Post object.
     */

    @PostMapping("/comment")
    public Post addComment(@RequestParam String postId, @Valid @RequestBody Comment comment){
        return postService.addComment(postId, comment);
    }
}
