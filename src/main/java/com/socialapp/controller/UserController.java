package com.socialapp.controller;

import com.socialapp.model.User;
import com.socialapp.repository.UserRepository;
import com.socialapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = new UserService(userRepository, passwordEncoder);
    }

    /**
     * Retrieves a list of all users.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/users}
     *
     * @return A {@link List} of all {@link User} objects.
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user, hashing the plain-text password provided in the request.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/users}
     * @param user The user object to save.
     * @return The saved {@link User} with a hashed password.
     */
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        // Encode the user's plain-text password before saving.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves a single user by their email address.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/users/email/{email}}
     *
     * @param email The email address of the user to find.
     * @return The found {@link User} object.
     * @throws ResponseStatusException with status 404 if the user does not exist.
     */
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
    }

    /**
     * Allows the {@code userId} to accept a friend request from {@code friendId}
     * @param userId receivers Id
     * @param friendId senders Id
     * @return if the friend request was successfully accepted or not
     */
    @PostMapping("/users/accept-friend-request")
    public String acceptFriendRequest(@RequestParam String userId, @RequestParam String friendId) {
        userService.acceptFriendRequest(userId, friendId);
        return "Friend request accepted";
    }
}
