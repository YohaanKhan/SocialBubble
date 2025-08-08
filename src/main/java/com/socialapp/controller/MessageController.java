package com.socialapp.controller;

import com.socialapp.model.Message;
import com.socialapp.repository.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // API ENDPOINTS

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
    public Message createMessage(@Valid @RequestBody Message message) {
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
