package com.example.controller.rest;

import com.example.dto.*;
import com.example.exception.AdvertisementNotFoundException;
import com.example.exception.ChatNotFoundException;
import com.example.exception.MessageNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.Advertisement;
import com.example.model.Chat;
import com.example.model.Message;
import com.example.model.User;
import com.example.service.IChatService;
import com.example.service.IMessageService;
import com.example.service.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/chats")
public class ChatAndMessageController {
    private static final Logger logger = LoggerFactory.getLogger(ChatAndMessageController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IChatService chatService;
    @Autowired
    private IMessageService messageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChats(Authentication authentication) {
        logger.info("GET /api/chats - запрос на получение чатов");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        List<ChatDTO> chats = chatService.findUsersChats(user);
        if (chats == null || chats.isEmpty()) {
            logger.info("У пользователя {} нет чатов", user.getLogin());
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(chats);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatItemDTO> getChatAndMessage(Authentication authentication, @PathVariable("id") int id) {
        logger.info("GET /api/chats/{id} - запрос на получение чата");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        ChatItemDTO chat = chatService.findChatById(user,id);
        if (chat == null) {
            throw new ChatNotFoundException("Чат с ID " + id + " не найден");
        }
        List<MessageDTO> messages = messageService.findMessagesFromChat(chat.getId());
        if (messages == null || messages.isEmpty()) {
            logger.info("Нет сообщений в чате {}", chat.getId());
            chat.setMessageList(Collections.emptyList());
        }else{
            chat.setMessageList(messages);
        }
        return ResponseEntity.ok(chat);
    }

    @GetMapping(value = "/{id}/{idmessage}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMessage(Authentication authentication, @PathVariable("idmessage") int id) {
        logger.info("GET /api/chats/{id}/{idmessage} - запрос на получение сообщения");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Message message = messageService.find(id);
        if (message == null || message.getMessage().isEmpty()) {
            throw new MessageNotFoundException("Сообщение с ID " + id + " не найдено");
        }
        if (!Objects.equals(message.getUser(), user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете изучать только свои сообщения"));
        }
        MessageDTO messageanswer = new MessageDTO(
                message.getId(),
                message.getUser().getLogin(),
                message.getMessage(),
                message.getDate()
        );
        return ResponseEntity.ok(messageanswer);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postChat(Authentication authentication, @Valid @RequestBody NewChatDTO newChat) {
        logger.info("Post /api/chats/{id} - создание чата");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        User user2 = userService.find(newChat.getUser2());
        if (user2 == null) {
            throw new UserNotFoundException("Пользователь с ID " + newChat.getUser2() + " не найден");
        }
        Chat chat = new Chat(user2,user);
        chatService.save(chat);
        logger.info("Сохранен чат с ID: {}", chat.getId());
        ChatResultDTO result = new ChatResultDTO(
                chat.getId(),
                user2.getLogin()
        );

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postMessageToChat(Authentication authentication, @PathVariable("id") int id, @Valid @RequestBody NewMessageDTO newMessage) {
        logger.info("Post /api/chats/{id} - отправка сообщения");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Chat chat = chatService.find(id);
        if (chat == null) {
            throw new ChatNotFoundException("Чат с ID " + id + " не найден");
        }
        if (!(Objects.equals(chat.getBuyer(), user)||Objects.equals(chat.getSeller(), user))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете писать только в свой чат!"));
        }
        Message message = new Message(user, chat, newMessage.getMessage());
        messageService.save(message);
        logger.info("Созданное сообщение с ID: {}", message.getId());
        MessageDTO result = new MessageDTO(
                message.getId(),
                user.getLogin(),
                message.getMessage(),
                message.getDate()
        );

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{idmessage}")
    public ResponseEntity<?> changeMessage(Authentication authentication,
                                           @PathVariable("id") int id,
                                           @PathVariable("idmessage") int idmessage,
                                           @Valid @RequestBody NewMessageDTO newMessage) {
        logger.info("Put /api/chats/id/idmessage - изменение сообщения с idmessage: {}", id);
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Message message  = messageService.find(idmessage);
        if (message == null) {
            throw new MessageNotFoundException("Сообщение с ID " + idmessage + " не найдено");
        }
        if (!(Objects.equals(message.getUser(), user))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете изменять только своё сообщение!"));
        }
        message.setMessage(newMessage.getMessage());
        messageService.update(message);
        logger.info("Чат с ID: {} удален", idmessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/{idmessage}")
    public ResponseEntity<?> deleteMessage(Authentication authentication, @PathVariable("idmessage") int idmessage) {
        logger.info("DELETE /api/chats/id - удаление сообщения с id: {}", idmessage);
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Message message  = messageService.find(idmessage);
        if (message == null) {
            throw new MessageNotFoundException("Сообщение с ID " + idmessage + " не найдено");
        }
        if (!(Objects.equals(message.getUser(), user))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете удалять только своё сообщение!"));
        }

        messageService.delete(idmessage);
        logger.info("Сообщение с ID: {} удалено", idmessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChat(Authentication authentication, @PathVariable("id") int id) {
        logger.info("DELETE /api/chats/id - удаление чата с id: {}", id);
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Chat chat = chatService.find(id);
        if (chat == null) {
            throw new ChatNotFoundException("Чат с ID " + id + " не найден");
        }
        if (!(Objects.equals(chat.getBuyer(), user)||Objects.equals(chat.getSeller(), user))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете удалить только свой чат!"));
        }
        chatService.delete(id);
        logger.info("Чат с ID: {} удален", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
