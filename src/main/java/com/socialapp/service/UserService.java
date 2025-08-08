package com.socialapp.service;


import com.socialapp.model.User;
import com.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Service class for handling user-related business logic.
 * <p>
 * This includes user creation, profile management, and social interactions
 * like managing friend lists.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user by hashing their password and saving them to the database.
     *
     * @param user The new {@link User} object containing a plain-text password.
     * @return The saved {@link User} entity with the password securely hashed.
     */
    public User saveUser (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Accepts a friend request by adding both users to each other's friend list
     * @param userId The Id of the user accepting the request
     * @param friendId The Id of the user to be added as a friend
     * @throws RuntimeException Occurs when either user is not found or if the users are same
     */
    public void acceptFriendRequest( String userId, String friendId) {
        if (userId.equals(friendId)) {
            throw new RuntimeException("Cannot friend yourself.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found." + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found." + friendId));

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userRepository.save(user);
        userRepository.save(friend);
    }
}
