package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    @NotBlank(message = "Заполните логин")
    private String username;
    @NotBlank(message = "Введите пароль")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
