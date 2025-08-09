package com.socialapp.service;

import com.socialapp.model.FriendRequest;
import com.socialapp.repository.FriendRequestRepository;
import com.socialapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class for handling friend request-related business logic.
 * It manages the lifecycle of a friend request, including its creation,
 * acceptance, and rejection.
 */
@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(PostRepository postRepository, FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     * Creates and sends a new friend request from one user to another.
     * <p>
     * It sets the initial status to PENDING and records the creation time.
     *
     * @param senderId   The unique Id of the user sending the request.
     * @param receiverId The unique Id of the user receiving the request.
     * @return The newly created {@link FriendRequest} object.
     * @throws IllegalArgumentException if a user attempts to send a request to themselves.
     */
    public FriendRequest sendFriendRequest(String senderId, String receiverId) {
        FriendRequest request = new FriendRequest();
        request.setSenderId(senderId);
        request.setReceiverId(receiverId);
        request.setCreatedAt(LocalDateTime.now());
        return friendRequestRepository.save(request);
    }

    /**
     * Rejects a friend request by updating its status to REJECTED.
     *
     * @param requestId The unique identifier of the friend request to be rejected.
     * @throws RuntimeException if no friend request with the given ID is found.
     */
    public void rejectFriendRequest(String requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend Request Not Found"));
        request.setStatus(FriendRequest.Status.REJECTED);
        friendRequestRepository.save(request);
    }
}
