package com.example.dao;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.CommentsDTO;
import com.example.model.Chat;
import com.example.model.Comments;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentsDAO extends HibernateAbstractDao<Comments, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(CommentsDAO.class);

    public CommentsDAO() {
        super(Comments.class);
        logger.debug("CommentsDAO created");
    }

    public List<CommentsDTO> findAdvertisementComments(int idadvertisement) {
        logger.debug("Показ объявления с idadvertisement {}", idadvertisement);
        Session session = getCurrentSession();

        String hql = """
            SELECT new com.example.dto.CommentsDTO(c.comment, c.date)
            FROM Comments c
            WHERE c.advertisement.id = :idadvertisement
        """;

        Query<CommentsDTO> query = session.createQuery(hql, CommentsDTO.class);
        query.setParameter("id", idadvertisement);
        return query.getResultList();
    }
}