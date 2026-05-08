package com.example.service;


import com.example.dao.ChatDAO;
import com.example.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("chatServiceSQL")
public class ChatServiceSQL extends GenericServiceImpl<Chat, Integer, ChatDAO>
implements IChatService{
    private static final Logger logger = LoggerFactory.getLogger(ChatServiceSQL.class);

    @Autowired

    public ChatServiceSQL(ChatDAO chatDAO) {
        super(chatDAO);
    }

    @Override
    public Integer save(Chat chat) {
        logger.debug("Сохранение чата: id до сохранения={}", chat.getId());
        Integer id = super.save(chat);
        logger.debug("Чат сохранен, id после сохранения={}", id);
        return id;
    }

    @Override
    public void update(Chat chat) {
        logger.debug("Изменение чата с id = {}",  chat.getId());
        super.update(chat);
    }

    @Override
    public List<Chat> findAll() {
        logger.debug("Поиск всех чатов");
        return  super.findAll();
    }
    @Override
    public Chat find(Integer id) {
        logger.debug("Поиск чата с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление чата с id: {}",id);
        super.delete(id);
    }
}
