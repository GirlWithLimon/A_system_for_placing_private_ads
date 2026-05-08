package com.example.service;


import com.example.dao.ProfileDAO;
import com.example.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("profileServiceSQL")
public class ProfileServiceSQL extends GenericServiceImpl<Profile, Integer, ProfileDAO>
implements IProfileService{
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceSQL.class);

    @Autowired

    public ProfileServiceSQL(ProfileDAO profileDAO) {
        super(profileDAO);
    }

    @Override
    public Integer save(Profile profile) {
        logger.debug("Сохранение профиля: название={}, id до сохранения={}", profile.getName(), profile.getId());
        Integer id = super.save(profile);
        logger.debug("Профиль сохранен, id после сохранения={}", id);
        return id;
    }
    @Override
    public void update(Profile profile) {
        logger.debug("Изменение профиля с id ={}",  profile.getId());
        super.update(profile);
    }

    @Override
    public List<Profile> findAll() {
        logger.debug("Поиск всех профилей");
        return  super.findAll();
    }
    @Override
    public Profile find(Integer id) {
        logger.debug("Поиск профиля с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление профиля с id: {}",id);
        super.delete(id);
    }
}
