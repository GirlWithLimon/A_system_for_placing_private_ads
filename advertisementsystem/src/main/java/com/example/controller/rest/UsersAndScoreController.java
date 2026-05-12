package com.example.controller.rest;


import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.dto.UserProfileScoreDTO;
import com.example.dto.UserScoreDTO;
import com.example.exception.AdvertisementNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.service.IProfileService;
import com.example.service.IScoreService;
import com.example.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersAndScoreController {
    private static final Logger logger = LoggerFactory.getLogger(UsersAndScoreController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private IScoreService scoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserScoreDTO>> getUsers() {
        logger.info("GET /api/users - запрос на получение пользователей");
        List<UserScoreDTO> userScore = userService.findUsersWithScore();
        return ResponseEntity.ok(userScore);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileScoreDTO> getUserItem(@PathVariable("id") int id) {
        logger.info("GET /api/users/{id} - запрос на получение пользователя");
        UserProfileScoreDTO userItem = userService.findUserItem(id);
        if (userItem == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        return ResponseEntity.ok(userItem);
    }
}
