package com.example.controller.rest;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.exception.AdvertisementNotFoundException;
import com.example.model.Advertisement;
import com.example.model.ProductsCategory;
import com.example.service.IAdvertisementService;
import com.example.service.ICommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);

    @Autowired
    private IAdvertisementService advertisementServiceSQL;
    @Autowired
    private ICommentsService commentsServiceSQL;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdvertisementsDTO>> getAdvertisements() {
        logger.info("GET /api/advertisements - запрос на получение объявлений");
        List<AdvertisementsDTO> advertisements = advertisementServiceSQL.findAdvertisementsWithSellerInfo();
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdvertisementItemDTO> getAdvertisementItem(@PathVariable("id") int id) {
        logger.info("GET /api/advertisements/{id} - запрос на получение объявления");
        AdvertisementItemDTO advertisement = advertisementServiceSQL.findAdvertisementItem(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisement.setComments(commentsServiceSQL.findAdvertisementComments(id));
        return ResponseEntity.ok(advertisement);
    }
    @GetMapping(params = "category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdvertisementsDTO>> getAdvertisementWithCategory(@RequestParam("category") ProductsCategory productsCategory) {
        logger.info("GET /api/advertisements/{category} - запрос на получение объявления по категориям");
        List<AdvertisementsDTO>  advertisement = advertisementServiceSQL.findAdvertisementWithCategory(productsCategory);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с категорией " + productsCategory + " не найдено");
        }
        return ResponseEntity.ok(advertisement);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAdvertisement(Authentication authentication, @PathVariable("id") int id) {
        logger.info("DELETE /api/advertisements/id - удаление объявления с id: {}", id);
        Advertisement advertisement = advertisementServiceSQL.find(id);
        if (advertisement == null) {
            throw new AdvertisementNotFoundException("Объявление с ID " + id + " не найдено");
        }
        advertisementServiceSQL.delete(id);
        logger.info("Объявление удалено с ID: {}", id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
