package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /**
     * Registers a new account if username does not already exist
     * Username must not be blank and at least 4 characters
     * @param account Account
     * @return Status code 400 if invalid username
     *         Status code 409 if username already exists
     *         Status code 200 and JSON of new account if successful
     */
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400)
                                 .body(account);
        }
        Account newAccount = accountService.registerAccount(account);
        if (newAccount == null) {
            return ResponseEntity.status(409)
                                 .body(account);
        }
        else return ResponseEntity.status(200)
                                  .body(newAccount);
    }

    /**
     * Attempts to login
     * @param account Account with username and password
     * @return Status code 401 if failed
     *         Status code 200 and JSON of account if successful
     */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.login(account);
        if (loginAccount == null) {
            return ResponseEntity.status(401)
                                 .body(account);
        }
        else return ResponseEntity.status(200)
                                  .body(loginAccount);
    }

    /**
     * Posts a new message
     * Message text must not be blank or over 255 characters
     * Posted by user id must exist
     * @param message Message
     * @return Status code 400 if failed
     *         Status code 200 and JSON of new message if successful
     */
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        Message newMessage = messageService.postNewMessage(message);
        if (newMessage == null) {
            return ResponseEntity.status(400)
                                 .body(message);
        }
        else return ResponseEntity.status(200)
                                  .body(newMessage);
    }

    /**
     * Gets a list of all messages
     * @return Status code 200 and JSON of list of all messages
     */
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200)
                             .body(allMessages);
    }

    /**
     * Gets message by message id
     * @param id Message id
     * @return Status code 200 and JSON of message, or blank if message does not exist
     */
    @GetMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.status(200)
                             .body(message);
    }

    /**
     * Deletes message by message id
     * @param id Message id
     * @return Status code 200
     *         1 in response body if a message was deleted, else blank
     */
    @DeleteMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable int id) {
        Integer updated = messageService.deleteMessageById(id);
        return ResponseEntity.status(200)
                             .body(updated);
    }

    /**
     * Updates message by message id
     * Message text must not be blank or over 255 characters
     * @param id Message id
     * @param message Updated message
     * @return Status code 400 if failed
     *         Status code 200 and 1 in message body if successful
     */
    @PatchMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> patchMessageById(@PathVariable int id, @RequestBody Message message) {
        Integer updated = messageService.patchMessageById(id, message);
        if (updated == null) {
            return ResponseEntity.status(400)
                                 .body(updated);
        }
        else return ResponseEntity.status(200)
                                  .body(updated);
    }

    /**
     * Gets list of all messages posted by a user
     * @param accountId User id
     * @return Status code 200 and JSON of messages
     */
    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesFromUserId(@PathVariable int accountId) {
        List<Message> allMessages = messageService.getAllMessagesFromUserId(accountId);
        return ResponseEntity.status(200)
                             .body(allMessages);
    }
}
