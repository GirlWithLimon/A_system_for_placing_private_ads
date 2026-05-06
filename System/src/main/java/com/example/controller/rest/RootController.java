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

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("profile", "/api/profile");
        endpoints.put("advertisement", "/api/advertisement");
        response.put("endpoints", endpoints);

        return ResponseEntity.ok(response);
    }
}