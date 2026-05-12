package com.example.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Advertisement API");
        response.put("version", "1.0");
        response.put("status", "running");

        Map<String, Object> endpoints = new HashMap<>();

        // Auth
        endpoints.put("POST /api/login", "Аутентификация");
        endpoints.put("POST /api/register", "Регистрация");

        // Advertisements (public)
        endpoints.put("GET /api/advertisements", "Список объявлений");
        endpoints.put("GET /api/advertisements/{id}", "Объявление по ID");
        endpoints.put("GET /api/advertisements?category={category}", "Фильтр по категории");
        endpoints.put("DELETE /api/advertisements/{id}", "Удалить объявление (ADMIN)");

        // My advertisements
        endpoints.put("GET /api/my/advertisements", "Мои объявления");
        endpoints.put("POST /api/my/advertisements", "Создать объявление");
        endpoints.put("PATCH /api/my/advertisements/{id}", "Изменить объявление");
        endpoints.put("PATCH /api/my/advertisements/{id}/byestatus", "Оплатить топ (ADMIN)");
        endpoints.put("DELETE /api/my/advertisements/{id}", "Удалить объявление");

        // Profile
        endpoints.put("GET /api/my/profile", "Мой профиль");
        endpoints.put("PUT /api/my/profile", "Изменить профиль");

        // Chats
        endpoints.put("GET /api/chats", "Список чатов");
        endpoints.put("GET /api/chats/{id}", "Чат с сообщениями");
        endpoints.put("POST /api/chats", "Создать чат");
        endpoints.put("POST /api/chats/{id}", "Отправить сообщение");
        endpoints.put("PUT /api/chats/{id}/{idmessage}", "Изменить сообщение");
        endpoints.put("DELETE /api/chats/{id}", "Удалить чат");
        endpoints.put("DELETE /api/chats/{id}/{idmessage}", "Удалить сообщение");

        // Users & Scores
        endpoints.put("GET /api/users", "Список пользователей");
        endpoints.put("GET /api/users/{id}", "Пользователь по ID");
        endpoints.put("GET /api/users/{id}/score", "Оценки пользователя");
        endpoints.put("POST /api/users/{id}/score", "Поставить оценку");
        endpoints.put("PUT /api/users/{id}/score/{idscore}", "Изменить оценку");
        endpoints.put("DELETE /api/users/{id}/score/{idscore}", "Удалить оценку");
        endpoints.put("DELETE /api/users/{id}/score/{idscore}/admin", "Удалить оценку (ADMIN)");

        response.put("endpoints", endpoints);

        return ResponseEntity.ok(response);
    }
}