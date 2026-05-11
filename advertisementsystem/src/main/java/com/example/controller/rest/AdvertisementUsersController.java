package com.example.controller.rest;

import com.example.dto.*;
import com.example.exception.AdvertisementNotFoundException;
import com.example.model.Advertisement;
import com.example.model.User;
import com.example.service.AdvertisementServiceSQL;
import com.example.service.CommentsServiceSQL;
import com.example.service.UserServiceSQL;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user/advertisements")
public class AdvertisementUsersController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementUsersController.class);

    @Autowired
    private AdvertisementServiceSQL advertisementServiceSQL;
    @Autowired
    private CommentsServiceSQL commentsServiceSQL;
    @Autowired
    private UserServiceSQL userServiceSQL;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertisements(Authentication authentication) {
        logger.info("GET /api/user/advertisements - запрос на получение объявлений пользователя");
        User seller = findUser(authentication);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пользователь не найден"));
        }
        List<AdvertisementsUsersDTO> advertisements = advertisementServiceSQL.findAdvertisementsUsers(seller.getId());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertisementItem(@PathVariable("id") int id) {
        logger.info("GET /api/user/advertisements/{id} - запрос на получение объявления пользователя");
        AdvertisementUsersDTO advertisement = advertisementServiceSQL.findAdvertisementUsersItem(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisement.setComments(commentsServiceSQL.findAdvertisementComments(id));
        return ResponseEntity.ok(advertisement);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAdvertisement(Authentication authentication, @Valid @RequestBody NewAdvertisementDTO advertisementDTO) {
        logger.info("POST /api/user/advertisements - создание объявления: {}", advertisementDTO.getTitle());
        User seller = findUser(authentication);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пользователь не найден"));
        }
        Advertisement advertisement = new Advertisement(
                seller,
                advertisementDTO.getTitle(),
                advertisementDTO.getCategory(),
                advertisementDTO.getPrice()
        );

        if (advertisementDTO.getDescription() != null) {
            advertisement.setDescription(advertisementDTO.getDescription());
        }

        advertisementServiceSQL.save(advertisement);
        logger.info("Объявление создано с ID: {}", advertisement.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeAdvertisement(Authentication authentication, @PathVariable("id") int id, @Valid @RequestBody ChangeAdvertisementDTO advertisementDTO) {
        logger.info("PUT /api/user/advertisements/id - изменение объявления с id: {}", id);
        User seller = findUser(authentication);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пользователь не найден"));
        }
        if (!Objects.equals(advertisementServiceSQL.find(id).getSeller().getId(), seller.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете редактировать только свои объявления"));
        }
        Advertisement advertisement = advertisementServiceSQL.find(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setCategory(advertisementDTO.getCategory());
        advertisement.setStatus(advertisementDTO.getStatus());
        if (advertisementDTO.getDescription() != null) {
            advertisement.setDescription(advertisementDTO.getDescription());
        }
        advertisementServiceSQL.update(advertisement);
        logger.info("Объявление изменено с ID: {}", advertisement.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    //для проплачивания
    @PutMapping(value = "/{id}/byestatus", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeAdvertisementSaleStatus(@PathVariable("id") int id, @Valid @RequestBody AdvertisementByeDTO advertisementDTO) {
        logger.info("PUT /api/user/advertisements/id - изменение статуса проплаченности объявления с id: {}", id);
        Advertisement advertisement = advertisementServiceSQL.find(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisement.setByeStatus(advertisementDTO.getByeStatus());
        advertisementServiceSQL.update(advertisement);
        logger.info("Объявление с ID именен статус проплачивания: {}", advertisement.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAdvertisement(Authentication authentication, @PathVariable("id") int id) {
        logger.info("DELETE /api/user/advertisements/id - удаление объявления с id: {}", id);
        User seller = findUser(authentication);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пользователь не найден"));
        }
        if (!Objects.equals(advertisementServiceSQL.find(id).getSeller().getId(), seller.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете редактировать только свои объявления"));
        }
        Advertisement advertisement = advertisementServiceSQL.find(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisementServiceSQL.delete(id);
        logger.info("Объявление удалено с ID: {}", id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User findUser(Authentication authentication){
        String currentUserLogin = authentication.getName();
        User seller = userServiceSQL.findByUsername(currentUserLogin);
        return seller;
    }
}
