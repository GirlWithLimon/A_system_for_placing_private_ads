package com.example.dao;

import com.example.model.Advertisement;
import com.example.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDAO extends HibernateAbstractDao<Chat, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ChatDAO.class);

    public ChatDAO() {
        super(Chat.class);
        logger.debug("ChatDAO created");
    }
}