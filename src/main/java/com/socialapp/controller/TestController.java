package com.socialapp.controller;

import com.socialapp.model.FriendRequest;
import com.socialapp.model.Message;
import com.socialapp.model.Post;
import com.socialapp.model.User;
import com.socialapp.repository.FriendRequestRepository;
import com.socialapp.repository.MessageRepository;
import com.socialapp.repository.PostRepository;
import com.socialapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


/**
 * REST controller for testing application entities.
 * <p>
 * Provides endpoints for Users, Posts, Messages, and Friend Requests. In production,
 * splitting is preferred.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MessageRepository messageRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the TestController with all required repository and service dependencies.
     * <p>
     * Using constructor injection is a best practice as it ensures all mandatory
     * dependencies are provided upon instantiation and allows them to be declared final.
     *
     * @param userRepository          Repository for User data access.
     * @param postRepository          Repository for Post data access.
     * @param messageRepository       Repository for Message data access.
     * @param friendRequestRepository Repository for FriendRequest data access.
     * @param passwordEncoder         Service for encoding passwords.
     */
    public TestController(UserRepository userRepository, PostRepository postRepository,
                          MessageRepository messageRepository, FriendRequestRepository friendRequestRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.messageRepository = messageRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User Endpoints

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
    public User createUser(@RequestBody User user) {
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

    // Post Endpoints

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
    public Post createPost(@RequestBody Post post) {
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

    // Friend Request Endpoints

    /**
     * Creates a new friend request.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/friend-requests}
     *
     * @param friendRequest The friend request object from the request body.
     * @return The saved {@link FriendRequest} object with a creation timestamp.
     */
    @PostMapping("/friend-requests")
    public FriendRequest createFriendRequest(@RequestBody FriendRequest friendRequest) {
        // Ensure the creation timestamp is set on the server-side.
        friendRequest.setCreatedAt(LocalDateTime.now());
        return friendRequestRepository.save(friendRequest);
    }

    /**
     * Retrieves all friend requests sent by a specific user.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/friend-request/sent/{senderId}}
     *
     * @param senderId The ID of the user who sent the requests.
     * @return A list of sent friend requests.
     */
    @GetMapping("/friend-request/sent/{senderId}")
    public List<FriendRequest> getSentFriendRequests(@PathVariable String senderId) {
        return friendRequestRepository.findBySenderId(senderId);
    }

    /**
     * Retrieves all friend requests received by a user, filtered by status.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/friend-request/received/{receiverId}/status/{status}}
     *
     * @param receiverId The ID of the user who received the requests.
     * @param status     The status to filter by (e.g., PENDING, ACCEPTED).
     * @return A list of received friend requests matching the status.
     */
    @GetMapping("/friend-request/received/{receiverId}/status/{status}")
    public List<FriendRequest> getReceivedFriendRequests(@PathVariable String receiverId, @PathVariable FriendRequest.Status status) {
        return friendRequestRepository.findByReceiverIdAndStatus(receiverId, status);
    }

    // Message Endpoints

    /**
     * Retrieves the conversation between two specific users.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/messages/sent/{senderId}/to/{receiverId}}
     *
     * @param senderId   The ID of the message sender.
     * @param receiverId The ID of the message receiver.
     * @return A list of messages exchanged between the two users.
     */
    @GetMapping("/messages/sent/{senderId}/to/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(
            @PathVariable String senderId, @PathVariable String receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    /**
     * Creates and sends a new message.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/messages}
     *
     * @param message The message object from the request body.
     * @return The saved {@link Message} object with a creation timestamp.
     */
    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        // Ensure the timestamp is set on the server-side to prevent client-side manipulation.
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    /**
     * Retrieves all unread messages for a specific user.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/messages/unread/{receiverId}}
     *
     * @param receiverId The ID of the user whose unread messages are to be retrieved.
     * @return A list of unread messages for the user.
     */
    @GetMapping("/messages/unread/{receiverId}")
    public List<Message> getUnreadMessages(@PathVariable String receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }
}