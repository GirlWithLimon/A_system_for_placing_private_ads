package com.example.controller.rest;

import com.example.dto.ChatDTO;
import com.example.dto.UserScoreDTO;
import com.example.model.User;
import com.example.service.IChatService;
import com.example.service.IProfileService;
import com.example.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IChatService chatService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatDTO>> getChats(Authentication authentication) {
        logger.info("GET /api/chats - запрос на получение чатов");
        String currentUserLogin = authentication.getName();
        User user = userService.findByUsername(currentUserLogin);
        List<ChatDTO> userScore = chatService.findUsersChats(user);
        return ResponseEntity.ok(userScore);
    }
}
