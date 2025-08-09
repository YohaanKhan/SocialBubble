package com.socialapp.repository;

import com.socialapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * It handles CRUD logic for User Entity.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
