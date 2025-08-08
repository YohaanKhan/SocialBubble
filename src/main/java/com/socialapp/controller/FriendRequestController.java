package com.socialapp.controller;

import com.socialapp.model.FriendRequest;
import com.socialapp.repository.FriendRequestRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class FriendRequestController {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestController(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    // API ENDPOINTS

    /**
     * Creates a new friend request.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/friend-requests}
     *
     * @param friendRequest The friend request object from the request body.
     * @return The saved {@link FriendRequest} object with a creation timestamp.
     */
    @PostMapping("/friend-requests")
    public FriendRequest createFriendRequest(@Valid @RequestBody FriendRequest friendRequest) {
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
}

