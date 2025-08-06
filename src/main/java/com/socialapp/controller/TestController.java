package com.socialapp.controller;

import com.socialapp.model.FriendRequest;
import com.socialapp.model.Message;
import com.socialapp.model.Post;
import com.socialapp.model.User;
import com.socialapp.repository.FriendRequestRepository;
import com.socialapp.repository.MessageRepository;
import com.socialapp.repository.PostRepository;
import com.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User Apis
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("You do not exist."));
    }
    // post APIs
    @GetMapping("/posts/author/{authorId}")
    public List<Post> getPostsByAuthor(@PathVariable String authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    @PostMapping("/users/posts")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/posts/liked/{userId}")
    public List<Post> getLikedPosts(@PathVariable String userId) {
        return postRepository.findByLikesContaining(userId);
    }

    // friend request
    @GetMapping("/friend-request/sent/{senderId}")
    public List<FriendRequest> getSentFriendRequests( @PathVariable String senderId) {
        return friendRequestRepository.findBySenderId(senderId);
    }

    @GetMapping("/friend-request/received/{receiverId}/status/{status}")
    public List<FriendRequest> getReceivedFriendRequests( @PathVariable String receiverId, @PathVariable FriendRequest.Status status) {
        return friendRequestRepository.findByReceiverIdAndStatus(receiverId, status);
    }

    @PostMapping("/friend-requests/")
    public FriendRequest createFriendRequest(@RequestBody FriendRequest friendRequest) {
        friendRequest.setSentAt(LocalDateTime.now());
        return friendRequestRepository.save(friendRequest);
    }

    //messages
    // Message Endpoints
    @GetMapping("/messages/sent/{senderId}/to/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(
            @PathVariable String senderId, @PathVariable String receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now()); // Ensure timestamp
        return messageRepository.save(message);
    }

    @GetMapping("/messages/unread/{receiverId}")
    public List<Message> getUnreadMessages(@PathVariable String receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }
}
