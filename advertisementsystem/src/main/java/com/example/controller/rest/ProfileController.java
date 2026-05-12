package com.example.controller.rest;


import com.example.dto.ProfileDTO;
import com.example.exception.ProfileNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.Profile;
import com.example.model.User;
import com.example.service.IProfileService;
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

@RestController
@RequestMapping("/api/my/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementUsersController.class);

    @Autowired
    private IUserService userServiceSQL;
    @Autowired
    private IProfileService profileService;

    @GetMapping()
    public ResponseEntity<?> getProfile(Authentication authentication) {
        logger.info("GET /api/my/profile - изменение профиля");
        String currentUserLogin = authentication.getName();
        User seller = userServiceSQL.findByUsername(currentUserLogin);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        Profile profile = profileService.find(seller.getProfile().getId());
        if (profile == null) {
            throw new ProfileNotFoundException("Профиль не найден");
        }
        logger.info("Профиль с ID: {}", profile.getId());

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeProfile(Authentication authentication, @Valid @RequestBody ProfileDTO profileDTO) {
        logger.info("Patch /api/my/profile - изменение профиля");
        String currentUserLogin = authentication.getName();
        User seller = userServiceSQL.findByUsername(currentUserLogin);
        if (seller == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        Profile profile = profileService.find(seller.getProfile().getId());
        if (profile == null) {
            throw new ProfileNotFoundException("Профиль не найден");
        }

        profile.setSecondName(profileDTO.getSecondName());
        profile.setName(profileDTO.getName());
        if(profileDTO.getFatherName()!= null) {
            profile.setFatherName(profileDTO.getFatherName());
        }
        if(profileDTO.getAddress()!= null) {
            profile.setAddress(profileDTO.getAddress());
        }
        profile.setNumber(profileDTO.getNumber());

        profileService.update(profile);
        logger.info("Изменен профиль с ID: {}", profile.getId());

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
