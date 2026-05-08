package com.example.dao;

import com.example.model.Advertisement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AdvertisementDAO extends HibernateAbstractDao<Advertisement, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementDAO.class);

    public AdvertisementDAO() {
        super(Advertisement.class);
        logger.debug("AdvertisementDAO created");
    }
}