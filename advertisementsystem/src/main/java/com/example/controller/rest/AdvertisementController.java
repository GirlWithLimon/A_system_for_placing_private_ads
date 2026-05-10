package com.example.controller.rest;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.dto.NewAdvertisementDTO;
import com.example.model.Advertisement;
import com.example.service.AdvertisementServiceSQL;
import com.example.service.CommentsServiceSQL;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Autowired
    private AdvertisementServiceSQL advertisementServiceSQL;
    @Autowired
    private CommentsServiceSQL commentsServiceSQL;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdvertisementsDTO>> getAdvertisements() {
        logger.info("GET /api/advertisements - запрос на получение объявлений");
        List<AdvertisementsDTO> advertisements = advertisementServiceSQL.findAdvertisementsWithSellerInfo();
        return ResponseEntity.ok(advertisements);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertisement> createAdvertisement(@Valid @RequestBody NewAdvertisementDTO advertisementDTO) {
        logger.info("POST /api/advertisement - создание объявления: {}", advertisementDTO.getTitle());

        Advertisement advertisement = new Advertisement(
                advertisementDTO.getTitle(),
                advertisementDTO.getCategory(),
                advertisementDTO.getPrice()
        );

        if (advertisementDTO.getDescription() != null) {
            advertisement.setDescription(advertisementDTO.getDescription());
        }

        advertisementServiceSQL.save(advertisement);
        logger.info("Книга создана с ID: {}", advertisement.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdvertisementItemDTO> getAdvertisementItem(@PathVariable("id") int id) {
        logger.info("GET /api/advertisements/{id} - запрос на получение объявления");
        AdvertisementItemDTO advertisement = advertisementServiceSQL.findAdvertisementItem(id);
        advertisement.setComments(commentsServiceSQL.findAdvertisementComments(id));
        return ResponseEntity.ok(advertisement);
    }
}
