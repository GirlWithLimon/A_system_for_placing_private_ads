package com.example.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idchat", nullable = false)
    private Chat chat;

    @Column(name="message",nullable = false)
    private String message;

    @Column(name="date",nullable = false)
    private LocalDateTime date;

    Message(){}
    Message(int id, User user, Chat chat, String message){
        this.id = id;
        this.user = user;
        this.chat = chat;
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public Message(User user, Chat chat, String message) {
        this.user = user;
        this.chat = chat;
        this.message = message;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
