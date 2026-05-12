package com.example.service;

import com.example.dto.ChatDTO;
import com.example.dto.ChatItemDTO;
import com.example.model.Chat;
import com.example.model.User;

import java.util.List;

public interface IChatService extends GenericService<Chat, Integer> {
    List<ChatDTO> findUsersChats(User user);
    ChatItemDTO findChatById(User user, int id);
}