package com.example.dao;

import com.example.model.Chat;
import com.example.model.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDAO extends HibernateAbstractDao<Score, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ScoreDAO.class);

    public ScoreDAO() {
        super(Score.class);
        logger.debug("ScoreDAO created");
    }
}