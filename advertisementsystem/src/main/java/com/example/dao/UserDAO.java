package com.example.dao;

import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends HibernateAbstractDao<User, Integer> {

    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String login) {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE login = :login", User.class);
        query.setParameter("login", login);
        return query.uniqueResult();
    }
}