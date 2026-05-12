package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public class NewMessageDTO {
    @NotBlank(message = "Сообщение не может быть пустым")
    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
