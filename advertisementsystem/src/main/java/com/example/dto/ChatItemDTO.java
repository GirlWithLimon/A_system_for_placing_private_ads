package com.example.dto;

import com.example.model.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ChatItemDTO implements Serializable {
    private int id;
    private String user2;
    private List<MessageDTO> messageList;

    public ChatItemDTO(){}
    public ChatItemDTO(int id, String user2){
        this.id=id;
        this.user2 = user2;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUser2() { return user2; }
    public void setUser2(String user2) { this.user2 = user2; }

    public List<MessageDTO> getMessageList() { return messageList; }
    public void setMessageList(List<MessageDTO> messageList) { this.messageList = messageList; }
}
