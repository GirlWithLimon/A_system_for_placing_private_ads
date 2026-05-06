package com.example.dao;

import com.example.model.Chat;
import com.example.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDAO extends HibernateAbstractDao<Message, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);

    public MessageDAO() {
        super(Message.class);
        logger.debug("MessageDAO created");
    }
}