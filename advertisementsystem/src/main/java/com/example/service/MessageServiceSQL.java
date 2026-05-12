package com.example.service;


import com.example.dao.MessageDAO;
import com.example.dto.MessageDTO;
import com.example.model.Chat;
import com.example.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("messageServiceSQL")
public class MessageServiceSQL extends GenericServiceImpl<Message, Integer, MessageDAO>
implements IMessageService{
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceSQL.class);

    @Autowired

    public MessageServiceSQL(MessageDAO messageDAO) {
        super(messageDAO);
    }

    @Override
    public Integer save(Message message) {
        logger.debug("Сохранение сообщения: id до сохранения={}", message.getId());
        Integer id = super.save(message);
        logger.debug("Сообщение сохранено, id после сохранения={}", id);
        return id;
    }

    @Override
    public void update(Message message) {
        logger.debug("Изменение сообщения с id ={}",  message.getId());
        super.update(message);
    }

    @Override
    public List<Message> findAll() {
        logger.debug("Поиск всех сообщений");
        return  super.findAll();
    }
    @Override
    public Message find(Integer id) {
        logger.debug("Поиск сообщения с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление сообщения с id: {}",id);
        super.delete(id);
    }
    @Override
    public List<MessageDTO> findMessagesFromChat(int idchat){
        return defaultRepository.findMessagesFromChat(idchat);
    }
}
