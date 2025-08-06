package com.socialapp.repository;

import com.socialapp.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);
    List<Post> findByLikesContaining(String userId);
}
