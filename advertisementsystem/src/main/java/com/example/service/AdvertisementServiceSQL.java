package com.example.service;


import com.example.dao.AdvertisementDAO;
import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementItemUsersDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.dto.AdvertisementsUsersDTO;
import com.example.model.Advertisement;
import com.example.model.ProductsCategory;
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

    @Override
    public List<AdvertisementsDTO> findAdvertisementsWithSellerInfo(){
       return defaultRepository.findAdvertisementsWithSellerInfo();
    }
    @Override
    public List<AdvertisementsUsersDTO> findAdvertisementsUsers(int idSeller){
        return defaultRepository.findAdvertisementsUsers(idSeller);
    }
    @Override
    public AdvertisementItemDTO findAdvertisementItem(int id){
        return defaultRepository.findAdvertisementItem(id);
    }
    @Override
    public AdvertisementItemUsersDTO findAdvertisementUsersItem(int id){
        return defaultRepository.findAdvertisementUsersItem(id);
    }
    @Override
    public List<AdvertisementsDTO> findAdvertisementWithCategory (ProductsCategory category){
        return defaultRepository.findAdvertisementWithCategory (category);
    }
}
