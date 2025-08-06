package com.socialapp.repository;

import com.socialapp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<Message> findByReceiverIdAndIsReadFalse(String receiverId);
}
