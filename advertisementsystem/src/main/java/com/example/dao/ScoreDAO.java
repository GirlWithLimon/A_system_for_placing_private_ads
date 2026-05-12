package com.example.dao;

import com.example.dto.ScoreDTO;
import com.example.dto.UserScoreDTO;
import com.example.model.Chat;
import com.example.model.Score;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreDAO extends HibernateAbstractDao<Score, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ScoreDAO.class);

    public ScoreDAO() {
        super(Score.class);
        logger.debug("ScoreDAO created");
    }

    public List<ScoreDTO> findAllScoreUsers(int idseller){

        logger.debug("Поиск всех оценок продавца с ID: {}", idseller);
        Session session = getCurrentSession();

        String hql = """
            SELECT new com.example.dto.ScoreDTO(
                s.id,
                s.buyer.login,
                s.advertisement.title,
                s.score,
                s.comment,
                s.date
            )
            FROM Score s
            WHERE s.seller.id = :idseller
            ORDER BY s.date DESC
        """;

        Query<ScoreDTO> query = session.createQuery(hql, ScoreDTO.class);
        query.setParameter("idseller", idseller);
        return query.getResultList();
    }
}