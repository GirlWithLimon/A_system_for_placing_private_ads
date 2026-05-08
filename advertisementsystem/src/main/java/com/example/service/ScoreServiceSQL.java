package com.example.service;


import com.example.dao.ScoreDAO;
import com.example.model.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("scoreServiceSQL")
public class ScoreServiceSQL extends GenericServiceImpl<Score, Integer, ScoreDAO>
implements IScoreService{
    private static final Logger logger = LoggerFactory.getLogger(ScoreServiceSQL.class);

    @Autowired

    public ScoreServiceSQL(ScoreDAO scoreDAO) {
        super(scoreDAO);
    }

    @Override
    public Integer save(Score score) {
        logger.debug("Сохранение оценки: id до сохранения={}", score.getId());
        Integer id = super.save(score);
        logger.debug("Оценка сохранена, id после сохранения={}", id);
        return id;
    }

    @Override
    public void update(Score score) {
        logger.debug("Изменение оценки с id ={}",  score.getId());
        super.update(score);
    }

    @Override
    public List<Score> findAll() {
        logger.debug("Поиск всех оценок");
        return  super.findAll();
    }
    @Override
    public Score find(Integer id) {
        logger.debug("Поиск оценок с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление оценок с id: {}",id);
        super.delete(id);
    }
}
