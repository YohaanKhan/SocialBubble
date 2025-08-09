package com.socialapp.controller;

import com.socialapp.model.FriendRequest;
import com.socialapp.repository.FriendRequestRepository;
import com.socialapp.service.FriendRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing friend request-related operations.
 * <p>
 * This controller provides public endpoints for creating, retrieving,
 * accepting and rejecting on a friend request..
 */
@RestController
@RequestMapping("/api/test/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestController(FriendRequestService friendRequestService, FriendRequestRepository friendRequestRepository) {
        this.friendRequestService = friendRequestService;
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     * Creates a new friend request.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/friend-requests}
     *
     * @param friendRequest The friend request object from the request body.
     * @return The saved {@link FriendRequest} object with a creation timestamp.
     */
    @PostMapping
    public FriendRequest createFriendRequest(@Valid @RequestBody FriendRequest friendRequest) {
        return friendRequestService.sendFriendRequest(friendRequest.getSenderId(), friendRequest.getReceiverId());
    }

    /**
     * Sends a new friend request using sender and receiver IDs.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/friend-requests/send}
     *
     * @param senderId The ID of the sender.
     * @param receiverId The ID of the receiver.
     * @return The saved {@link FriendRequest} object.
     */
    @PostMapping("/send")
    public FriendRequest sendFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return friendRequestService.sendFriendRequest(senderId, receiverId);
    }

    /**
     * Rejects an existing friend request.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/friend-requests/reject}
     *
     * @param requestId The ID of the friend request to reject.
     */
    @PostMapping("/reject")
    public void rejectFriendRequest(@RequestParam String requestId) {
        friendRequestService.rejectFriendRequest(requestId);
    }

    /**
     * Retrieves all friend requests sent by a specific user.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/friend-requests/sent/{senderId}}
     *
     * @param senderId The ID of the user who sent the requests.
     * @return A list of sent friend requests.
     */
    @GetMapping("/sent/{senderId}")
    public List<FriendRequest> getSentFriendRequests(@PathVariable String senderId) {
        return friendRequestRepository.findBySenderId(senderId); // Update to service if needed
    }

    /**
     * Retrieves all friend requests received by a user, filtered by status.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/friend-requests/received/{receiverId}/status/{status}}
     *
     * @param receiverId The ID of the user who received the requests.
     * @param status     The status to filter by (e.g., PENDING, ACCEPTED).
     * @return A list of received friend requests matching the status.
     */
    @GetMapping("/received/{receiverId}/status/{status}")
    public List<FriendRequest> getReceivedFriendRequests(@PathVariable String receiverId, @PathVariable FriendRequest.Status status) {
        return friendRequestRepository.findByReceiverIdAndStatus(receiverId, status); // Update to service if needed
    }
}