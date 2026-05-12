package com.example.controller.rest;


import com.example.dto.*;
import com.example.exception.AdvertisementNotFoundException;
import com.example.exception.ScoreNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.Advertisement;
import com.example.model.Score;
import com.example.model.User;
import com.example.service.IAdvertisementService;
import com.example.service.IProfileService;
import com.example.service.IScoreService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UsersAndScoreController {
    private static final Logger logger = LoggerFactory.getLogger(UsersAndScoreController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private IAdvertisementService advertisementService;
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
    @GetMapping(value = "/{id}/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScoreDTO>> getUserScores(@PathVariable("id") int id) {
        logger.info("GET /api/users/{id}/score - запрос на получение оценок пользователя");
        User user = userService.find(id);
        if (user == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        List<ScoreDTO> scors = scoreService.findAllScoreUsers(id);

        return ResponseEntity.ok(scors);
    }
    @GetMapping(value = "/{id}/score/{idscore}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScoreDTO> getUserScoreItem(@PathVariable("idscore") int idscore) {
        logger.info("GET /api/users/{id}/score/idscore - запрос на получение оценки пользователя");
        Score score = scoreService.find(idscore);
        if (score == null) {
            throw new ScoreNotFoundException("Оценка с ID " + idscore + " не найдена");
        }
        ScoreDTO result = new ScoreDTO(
                score.getId(),
                score.getBuyer().getLogin(),
                score.getAdvertisement().getTitle(),
                score.getScore(),
                score.getComment(),
                score.getDate()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{id}/score",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScoreDTO> postUserScoreItem(@PathVariable("id") int id, Authentication authentication, @Valid @RequestBody NewScoreDTO newScoreDTO) {
        logger.info("Post /api/users/{id}/score/idscore - запрос на получение оценки пользователя");
        User seller = userService.find(id);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        User buyer = userService.findByUsername(authentication.getName());
        if (buyer == null) {
            throw new UserNotFoundException("Пользователь  не найден");
        }
        Advertisement advertisement = advertisementService.find(newScoreDTO.getIdadvertisement());
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление  не найдено");
        }
        Score score = new Score(
                seller,
                buyer,
                advertisement,
                newScoreDTO.getScore(),
                newScoreDTO.getComment()
        );
        scoreService.save(score);
        ScoreDTO result = new ScoreDTO(
                score.getId(),
                score.getBuyer().getLogin(),
                score.getAdvertisement().getTitle(),
                score.getScore(),
                score.getComment(),
                score.getDate()
        );
        return ResponseEntity.ok(result);
    }
    @PutMapping(value = "/{id}/score/{idscore}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putUserScoreItem(@PathVariable("idscore") int idscore, Authentication authentication, @Valid @RequestBody NewScoreDTO newScoreDTO) {
        logger.info("Post /api/users/{id}/score/idscore - запрос на получение оценки пользователя");
        Score score = scoreService.find(idscore);
        if (score == null) {
            throw new ScoreNotFoundException("Оценка с ID " + idscore + " не найдена");
        }
        User buyer = userService.findByUsername(authentication.getName());
        if (buyer == null) {
            throw new UserNotFoundException("Пользователь  не найден");
        }
        if (!(Objects.equals(score.getBuyer(), buyer))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете изменять только свой комментарий"));
        }
        score.setScore(newScoreDTO.getScore());
        score.setComment(newScoreDTO.getComment());
        scoreService.save(score);
        ScoreDTO result = new ScoreDTO(
                score.getId(),
                score.getBuyer().getLogin(),
                score.getAdvertisement().getTitle(),
                score.getScore(),
                score.getComment(),
                score.getDate()
        );
        return ResponseEntity.ok(result);
    }
    @DeleteMapping(value = "/{id}/score/{idscore}")
    public ResponseEntity<?> deleteUserScoreItem(@PathVariable("idscore") int idscore, Authentication authentication) {
        logger.info("Post /api/users/{id}/score/idscore - запрос на получение оценки пользователя");
        Score score = scoreService.find(idscore);
        if (score == null) {
            throw new ScoreNotFoundException("Оценка с ID " + idscore + " не найдена");
        }
        User buyer = userService.findByUsername(authentication.getName());
        if (buyer == null) {
            throw new UserNotFoundException("Пользователь  не найден");
        }
        if (!(Objects.equals(score.getBuyer(), buyer))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете изменять только свой комментарий"));
        }
        scoreService.delete(idscore);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/score/{idscore}/admin")
    public ResponseEntity<?> deleteUserScoreItemAdmin(@PathVariable("idscore") int idscore, Authentication authentication) {
        logger.info("Post /api/users/{id}/score/idscore - запрос на получение оценки пользователя");
        Score score = scoreService.find(idscore);
        if (score == null) {
            throw new ScoreNotFoundException("Оценка с ID " + idscore + " не найдена");
        }
        scoreService.delete(idscore);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
