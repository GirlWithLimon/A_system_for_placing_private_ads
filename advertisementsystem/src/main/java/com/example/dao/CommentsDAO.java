package com.example.dao;

import com.example.model.Chat;
import com.example.model.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CommentsDAO extends HibernateAbstractDao<Comments, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(CommentsDAO.class);

    public CommentsDAO() {
        super(Comments.class);
        logger.debug("CommentsDAO created");
    }
}