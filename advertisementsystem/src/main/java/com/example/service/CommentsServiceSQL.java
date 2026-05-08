package com.example.service;


import com.example.dao.CommentsDAO;
import com.example.model.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("commentsServiceSQL")
public class CommentsServiceSQL extends GenericServiceImpl<Comments, Integer, CommentsDAO>
implements ICommentsService{
    private static final Logger logger = LoggerFactory.getLogger(CommentsServiceSQL.class);

    @Autowired

    public CommentsServiceSQL(CommentsDAO commentsDAO) {
        super(commentsDAO);
    }

    @Override
    public Integer save(Comments comments) {
        logger.debug("Сохранение комментария: id до сохранения={}", comments.getId());
        Integer id = super.save(comments);
        logger.debug("Комментарий сохранен, id после сохранения={}", id);
        return id;
    }
    @Override
    public void update(Comments comments) {
        logger.debug("Изменение комментария с id ={}",  comments.getId());
        super.update(comments);
    }

    @Override
    public List<Comments> findAll() {
        logger.debug("Поиск всех комментариев");
        return  super.findAll();
    }
    @Override
    public Comments find(Integer id) {
        logger.debug("Поиск комментария с id: {}",id);
        return  super.find(id);
    }
    @Override
    public void delete(Integer id) {
        logger.debug("Удаление комментария с id: {}",id);
        super.delete(id);
    }
}
