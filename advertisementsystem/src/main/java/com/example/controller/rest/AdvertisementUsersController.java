package com.example.controller.rest;

import com.example.dto.*;
import com.example.exception.AdvertisementNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.Advertisement;
import com.example.model.AdvertisementStatus;
import com.example.model.User;
import com.example.service.*;
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
@RequestMapping("/api/my/advertisements")
public class AdvertisementUsersController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementUsersController.class);

    @Autowired
    private IAdvertisementService advertisementServiceSQL;
    @Autowired
    private ICommentsService commentsServiceSQL;
    @Autowired
    private IUserService userServiceSQL;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertisements(Authentication authentication) {
        logger.info("GET /api/my/advertisements - запрос на получение объявлений пользователя");
        User seller = findUser(authentication);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        List<AdvertisementsUsersDTO> advertisements = advertisementServiceSQL.findAdvertisementsUsers(seller.getId());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertisementItem(@PathVariable("id") int id) {
        logger.info("GET /api/my/advertisements/{id} - запрос на получение объявления пользователя");
        AdvertisementItemUsersDTO advertisement = advertisementServiceSQL.findAdvertisementUsersItem(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisement.setComments(commentsServiceSQL.findAdvertisementComments(id));
        return ResponseEntity.ok(advertisement);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdvertisementChangeAndPostAnswerDTO> createAdvertisement(Authentication authentication, @Valid @RequestBody NewAdvertisementDTO advertisementDTO) {
        logger.info("POST /api/my/advertisements - создание объявления: {}", advertisementDTO.getTitle());
        User seller = findUser(authentication);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь с не найден");
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
        AdvertisementChangeAndPostAnswerDTO advertisementinfo = new AdvertisementChangeAndPostAnswerDTO(
                advertisementDTO.getTitle(),
                advertisementDTO.getCategory(),
                advertisementDTO.getPrice(),
                AdvertisementStatus.AСTIVE
        );
        if (advertisementDTO.getDescription() != null) {
            advertisementinfo.setDescription(advertisementDTO.getDescription());
        }

        return new ResponseEntity<>(advertisementinfo, HttpStatus.CREATED);
    }
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeAdvertisement(Authentication authentication, @PathVariable("id") int id, @RequestBody AdvertisementChangeAndPostAnswerDTO advertisementDTO) {
        logger.info("Patch /api/my/advertisements/id - изменение объявления с id: {}", id);
        User seller = findUser(authentication);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        Advertisement advertisement = advertisementServiceSQL.find(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        if (!Objects.equals(advertisementServiceSQL.find(id).getSeller().getId(), seller.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Вы можете редактировать только свои объявления"));
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
    @PatchMapping(value = "/{id}/byestatus", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeAdvertisementSaleStatus(@PathVariable("id") int id, @Valid @RequestBody AdvertisementByeDTO advertisementDTO) {
        logger.info("PATCH /api/my/advertisements/id - изменение статуса проплаченности объявления с id: {}", id);
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
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
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
