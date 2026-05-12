package com.example.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private int id;
    private String user;
    private String message;
    private LocalDateTime date;

    public MessageDTO() {}

    public MessageDTO(int id, String user, String message, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
