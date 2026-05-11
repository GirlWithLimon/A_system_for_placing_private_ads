package com.example.controller.rest;

import com.example.dto.AuthDTO;
import com.example.dto.RegisterUserDTO;
import com.example.model.Profile;
import com.example.model.User;
import com.example.model.UserRole;
import com.example.security.JwtTokenUtil;
import com.example.service.IProfileService;
import com.example.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProfileService profileService;

    /**
     * Аутентификация пользователя и получение JWT-токена
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Неверные учетные данные"));
        }
    }

    /**
     * Регистрация нового пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO registerRequest) {
        try {
            if (userService.findByUsername(registerRequest.getLogin()) != null) {
                throw new IllegalArgumentException("Пользователь с таким именем уже существует");
            }
            Profile profile = new Profile(
                    registerRequest.getSecondName(),
                    registerRequest.getName(),
                    registerRequest.getNumber()
            );
            if (registerRequest.getAddress()!=null){
                profile.setAddress(registerRequest.getAddress());
            }
            if (registerRequest.getFatherName()!=null){
                profile.setFatherName(registerRequest.getFatherName());
            }

            User newUser = userService.registerNewUser(
                    registerRequest.getLogin().trim(),
                    registerRequest.getPassword(),
                    profile,
                    UserRole.USER
            );

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(newUser.getLogin())
                    .password(newUser.getPassword())
                    .roles("USER")
                    .build();
            String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(Map.of(
                    "message", "Пользователь успешно зарегистрирован",
                    "token", token
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ошибка при регистрации: " + e.getMessage()));
        }
    }


}