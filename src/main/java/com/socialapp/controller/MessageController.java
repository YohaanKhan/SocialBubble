package com.socialapp.controller;

import com.socialapp.model.Message;
import com.socialapp.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing message-related operations.
 * <p>
 * This controller provides public endpoints for sending, retrieving,
 * marking message as read/unread on user conversations.
 */
@RestController
@RequestMapping("/api/test")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
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
    @GetMapping("/sent/{senderId}/to/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(
            @PathVariable String senderId, @PathVariable String receiverId) {
        return messageService.getMessagesBySenderAndReceiver(senderId, receiverId);
    }

    /**
     * Creates and sends a new message.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/messages}
     *
     * @param message The message object from the request body.
     * @return The saved {@link Message} object with a creation timestamp.
     */
    @PostMapping
    public Message createMessage(@Valid @RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    /**
     * Retrieves all unread messages for a specific user.
     * <p>
     * <b>Endpoint:</b> {@code GET /api/test/messages/unread/{receiverId}}
     *
     * @param receiverId The ID of the user whose unread messages are to be retrieved.
     * @return A list of unread messages for the user.
     */
    @GetMapping("/unread/{receiverId}")
    public List<Message> getUnreadMessages(@PathVariable String receiverId) {
        return messageService.getUnreadMessages(receiverId);
    }

    /**
     * Marks a message as read.
     * <p>
     * <b>Endpoint:</b> {@code POST /api/test/messages/read}
     *
     * @param messageId The ID of the message to mark as read.
     */
    @PostMapping("/read")
    public void markAsRead(@RequestParam String messageId) {
        messageService.markAsRead(messageId);
    }






}
