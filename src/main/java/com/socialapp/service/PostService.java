package com.socialapp.service;

import com.socialapp.model.Post;
import com.socialapp.model.Post.Comment;
import com.socialapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * a service that handles post-related logic, like adding likes and comments.
 */
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Adds a user's like to a specific post.
     * <p>
     * The user cannot like a post again, once he has liked a post.
     *
     * @param postId The unique Id of the post to be liked.
     * @param userId The unique Id of the user who is liking the post.
     * @return The updated {@link Post} object with the new like added.
     * @throws RuntimeException if no post with the given postId exists.
     */
    public Post addLike(String postId, String userId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        // To prevent duplicate likes
        if (!post.getLikes().contains(userId)) {
            post.getLikes().add(userId);
        }
        return postRepository.save(post);
    }

    /**
     * Adds a new comment to a specific post.
     * <p>
     * This method sets values for the comment, such as the creation
     * timestamp and a unique ID, before saving it.
     *
     * @param postId  The unique Id of the post to add the comment to.
     * @param comment The {@link Comment} object to be added. It must contain the authorId and text.
     * @return The updated {@link Post} object with the new comment included.
     * @throws RuntimeException if no post with the given postId exists.
     */
    public Post addComment(String postId, Comment comment){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setCreatedAt(LocalDateTime.now());
        post.getComments().add(comment);
        return postRepository.save(post);
    }
}
