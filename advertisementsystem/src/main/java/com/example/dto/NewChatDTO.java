package com.example.dto;

import com.example.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewChatDTO implements Serializable {
    private int id;
    private Integer user2;
    public NewChatDTO(){}
    public NewChatDTO(int user2) {
        this.user2 = user2;
    }
    public NewChatDTO(int id, int user2) {
        this.id=id;
        this.user2 = user2;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getUser2() { return user2; }
    public void setUser2(int user2) { this.user2 = user2; }

}
