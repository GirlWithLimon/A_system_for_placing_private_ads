package com.example.dao;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.UserProfileScoreDTO;
import com.example.dto.UserScoreDTO;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO extends HibernateAbstractDao<User, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String login) {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE login = :login", User.class);
        query.setParameter("login", login);
        return query.uniqueResult();
    }

    public List<UserScoreDTO> findUsersWithScore(){
        logger.debug("Показ пользователей и их оценки");
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.UserScoreDTO(
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM User u
    """;

        Query<UserScoreDTO> query = session.createQuery(hql, UserScoreDTO.class);
        return query.getResultList();
    }
    public UserProfileScoreDTO findUserItem(int id) {
        logger.debug("Показ объявления с id {}", id);
        Session session = getCurrentSession();

        String hql = """
         SELECT new com.example.dto.UserProfileScoreDTO(
            p.secondname,
            p.name,
            p.fathername,
            p.address,
            p.number,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM User u
        JOIN u.profile p
        WHERE u.id = :id
    """;

        Query<UserProfileScoreDTO> query = session.createQuery(hql, UserProfileScoreDTO.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}