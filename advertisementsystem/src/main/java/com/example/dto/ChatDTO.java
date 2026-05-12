package com.example.dto;

import java.io.Serializable;

public class ChatDTO implements Serializable {
    private int id;
    private String user2;

    public ChatDTO(){}
    public ChatDTO(int id, String user2){
        this.id=id;
        this.user2 = user2;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUser2() { return user2; }
    public void setUser2(String user2) { this.user2 = user2; }
}
