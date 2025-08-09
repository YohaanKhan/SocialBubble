package com.socialapp.service;

import com.socialapp.model.Message;
import com.socialapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling message-related business logic
 * such as sending messages, marking them as read,
 * and retrieving message history.
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Saves a new message to the database and sets its creation timestamp.
     *
     * @param message The {@link Message} object to be sent. It should contain the senderId,
     * receiverId, and content.
     * @return The saved {@link Message} entity with the server-set timestamp.
     */
    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    /**
     * Marks a specific message as read.
     *
     * @param messageId The unique Id of the message to be marked as read.
     * @throws RuntimeException if no message with the given messageId exists.
     */
    public void markAsRead(String messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setIsRead(true);
        messageRepository.save(message);
    }

    /**
     * Retrieves the conversation history between two specific users.
     *
     * @param senderId   The unique Id of the user who sent the messages.
     * @param receiverId The unique Id of the user who received the messages.
     * @return A {@link List} of {@link Message} objects exchanged between the two users.
     */
    public List<Message> getMessagesBySenderAndReceiver(String senderId, String receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    /**
     * Retrieves all unread messages for a specific user.
     *
     * @param receiverId The unique Id of the user whose unread messages are to be fetched.
     * @return A {@link List} of unread {@link Message} objects for the specified user.
     */
    public List<Message> getUnreadMessages(String receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }
}
