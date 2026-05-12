package com.example.service;

import com.example.dto.MessageDTO;
import com.example.model.Chat;
import com.example.model.Message;

import java.util.List;

public interface IMessageService extends GenericService<Message, Integer> {
    List<MessageDTO> findMessagesFromChat(int idchat);
}