package com.example.controller.rest;


import com.example.dto.AdvertisementsDTO;
import com.example.service.IProfileService;
import com.example.service.IScoreService;
import com.example.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<AdvertisementsDTO>> getUsers() {
        logger.info("GET /api/advertisements - запрос на получение объявлений");
        List<AdvertisementsDTO> advertisements = userService.findUsersWithScore();
        return ResponseEntity.ok(advertisements);
    }
}
