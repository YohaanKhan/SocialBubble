package com.socialapp.repository;


import com.socialapp.model.FriendRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * It handles CRUD logic for FriendRequest Entity.
 */
public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    List<FriendRequest> findBySenderId(String senderId);
    List<FriendRequest> findByReceiverId(String receiverId);
    List<FriendRequest> findByReceiverIdAndStatus(String receiverId, FriendRequest.Status status);
}
