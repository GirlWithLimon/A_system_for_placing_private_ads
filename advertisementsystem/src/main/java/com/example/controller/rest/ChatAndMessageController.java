package com.example.controller.rest;

import com.example.dto.*;
import com.example.exception.ChatNotFoundException;
import com.example.exception.MessageNotFoundException;
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
                    .body(Map.of("error", "Вы можете редактировать только свои объявления"));
        }
        MessageDTO messageanswer = new MessageDTO(
                message.getId(),
                message.getUser().getLogin(),
                message.getMessage(),
                message.getDate()
        );
        return ResponseEntity.ok(messageanswer);
    }
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> postMessageToChat(Authentication authentication, @PathVariable("id") int id, @Valid @RequestBody NewMessageDTO newMessage) {
        logger.info("Post /api/chats/{id} - отправка сообщения");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        Chat chat = chatService.find(id);
        if (chat == null) {
            throw new ChatNotFoundException("Чат с ID " + id + " не найден");
        }
        Message message = new Message(user, chat, newMessage.getMessage());
        messageService.save(message);
        logger.info("Объявление создано с ID: {}", message.getId());
        MessageDTO result = new MessageDTO(
                message.getId(),
                user.getLogin(),
                message.getMessage(),
                message.getDate()
        );

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
