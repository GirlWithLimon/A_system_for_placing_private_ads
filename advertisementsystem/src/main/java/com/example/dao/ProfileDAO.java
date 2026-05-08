package com.example.dao;

import com.example.model.Chat;
import com.example.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDAO extends HibernateAbstractDao<Profile, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(ProfileDAO.class);

    public ProfileDAO() {
        super(Profile.class);
        logger.debug("ProfileDAO created");
    }
}