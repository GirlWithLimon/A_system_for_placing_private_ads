package com.example.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatDTO implements Serializable {
    private int id;
    private String user2;
    private String lastmessage;
    private LocalDateTime timelastmessage;

    public ChatDTO(){}
    public ChatDTO(int id, String user2, String lastmessage, LocalDateTime timelastmessage){
        this.id=id;
        this.user2 = user2;
        this.lastmessage = lastmessage;
        this.timelastmessage = timelastmessage;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUser2() { return user2; }
    public void setUser2(String user2) { this.user2 = user2; }

    public String getLastmessage() { return lastmessage; }
    public void setLastmessage(String lastmessage) { this.lastmessage = lastmessage; }

    public LocalDateTime getTimelastmessage() { return timelastmessage; }
    public void setTimelastmessage(LocalDateTime timelastmessage) { this.timelastmessage = timelastmessage; }
}
