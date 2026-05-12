package com.example.dao;

import com.example.dto.ChatDTO;
import com.example.dto.ChatItemDTO;
import com.example.dto.CommentsDTO;
import com.example.model.Advertisement;
import com.example.model.Chat;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDAO extends HibernateAbstractDao<Chat, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ChatDAO.class);

    public ChatDAO() {
        super(Chat.class);
        logger.debug("ChatDAO created");
    }
    public List<ChatDTO> findUsersChats(User user){
        logger.debug("Показ чатов для пользователя с id", user.getId());
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.ChatDTO(
            c.id,
            CASE 
                WHEN c.seller.id = :iduser THEN c.buyer.login
                ELSE c.seller.login
            END,
            (SELECT m.message FROM Message m WHERE m.chat.id = c.id ORDER BY m.date DESC LIMIT 1),
            (SELECT m.date FROM Message m WHERE m.chat.id = c.id ORDER BY m.date DESC LIMIT 1)
        )
        FROM Chat c
        WHERE c.seller.id = :iduser OR c.buyer.id = :iduser
        ORDER BY 
            COALESCE((SELECT m.date FROM Message m WHERE m.chat.id = c.id ORDER BY m.date DESC LIMIT 1), c.id DESC) DESC
    """;

        Query<ChatDTO> query = session.createQuery(hql, ChatDTO.class);
        query.setParameter("iduser", user.getId());
        return query.getResultList();
    }

    public ChatItemDTO findChatById(User user, int id){
        logger.debug("Показ чата с id", id);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.ChatDTO(
            c.id,
            CASE 
                WHEN c.seller.id = :iduser THEN c.buyer.login
                ELSE c.seller.login
            END
        )
        FROM Chat c
        WHERE (c.seller.id = :iduser OR c.buyer.id = :iduser)
        AND c.id = :id
       
    """;

        Query<ChatItemDTO> query = session.createQuery(hql, ChatItemDTO.class);
        query.setParameter("iduser", user.getId());
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}