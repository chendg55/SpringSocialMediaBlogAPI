package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Posts a new message
     * Message text must not be blank or over 255 characters
     * Posted by user id must exist
     * @param message Message
     * @return New message if successful, else null
     */
    public Message postNewMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255)
            return null;
        if (accountRepository.existsById(message.getPostedBy()))
            return messageRepository.save(message);
        else return null;
    }

    /**
     * Gets list of all messages
     * @return List of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Gets message by message id
     * @param id Message id
     * @return Message or null if does not exist
     */
    public Message getMessageById(int id) {
        return messageRepository.findById(id)
                                .orElse(null);
    }

    /**
     * Deletes message by message id
     * @param id Message id
     * @return 1 if message was deleted, else null
     */
    public Integer deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;
        }
        else return null;
    }

    /**
     * Updates message by message id
     * Message text must not be blank or over 255 characters
     * @param id Message id
     * @param message Updated message
     * @return 1 if message was updated, else null
     */
    public Integer patchMessageById(int id, Message message) {
        if (!messageRepository.existsById(id))
            return null;
        else if (message.getMessageText().isBlank() || message.getMessageText().length() > 255)
            return null;
        else {
            Message newMessage = getMessageById(id);
            newMessage.setMessageText(message.getMessageText());
            messageRepository.save(newMessage);
            return 1;
        }
    }

    /**
     * Gets list of all messages posted by a user
     * @param id User id
     * @return List of messages, else null if none exists
     */
    public List<Message> getAllMessagesFromUserId(int id) {
        return messageRepository.findByPostedBy(id)
                                .orElse(null);
    }
}
