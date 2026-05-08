package com.example.service;


import com.example.dao.AdvertisementDAO;
import com.example.model.Advertisement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("advertisementServiceSQL")
public class AdvertisementServiceSQL extends GenericServiceImpl<Advertisement, Integer, AdvertisementDAO>
implements IAdvertisementService{
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementServiceSQL.class);

    @Autowired

    public AdvertisementServiceSQL(AdvertisementDAO advertisementDAO) {
        super(advertisementDAO);
    }

    @Override
    public Integer save(Advertisement advertisement) {
        logger.debug("Сохранение объявления: название={}, id до сохранения={}", advertisement.getTitle(), advertisement.getId());
        Integer id = super.save(advertisement);
        logger.debug("Объявление сохранено, id после сохранения={}", id);
        return id;
    }

    @Override
    public void update(Advertisement advertisement) {
        logger.debug("Изменение объявления с id ={}",  advertisement.getId());
        super.update(advertisement);
    }

    @Override
    public List<Advertisement> findAll() {
        logger.debug("Поиск всех объявлений");
        return  super.findAll();
    }
    @Override
    public Advertisement find(Integer id) {
        logger.debug("Поиск объявления с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление объявления с id: {}",id);
        super.delete(id);
    }
}
