package com.example.dao;

import com.example.dto.ChatItemDTO;
import com.example.dto.MessageDTO;
import com.example.model.Chat;
import com.example.model.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAO extends HibernateAbstractDao<Message, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);

    public MessageDAO() {
        super(Message.class);
        logger.debug("MessageDAO created");
    }

    public List<MessageDTO> findMessagesFromChat(int idchat){
        logger.debug("Показ сообщений чата с id", idchat);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.MessageDTO(
            m.id,
            m.user.login,
            m.message,
            m.date
        )
        FROM Message m
        WHERE m.chat.id = :idchat
        ORDER BY m.date ASC
       
    """;

        Query<MessageDTO> query = session.createQuery(hql, MessageDTO.class);
        query.setParameter("idchat", idchat);
        return query.getResultList();
    }
}