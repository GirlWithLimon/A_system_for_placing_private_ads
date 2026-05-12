package com.example.dao;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementItemUsersDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.dto.AdvertisementsUsersDTO;
import com.example.model.Advertisement;
import com.example.model.ProductsCategory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvertisementDAO extends HibernateAbstractDao<Advertisement, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(AdvertisementDAO.class);

    public AdvertisementDAO() {
        super(Advertisement.class);
        logger.debug("AdvertisementDAO created");
    }

    public List<AdvertisementsDTO> findAdvertisementsWithSellerInfo() {
        logger.debug("Поиск всех объявлений с информацией о продавце (сначала проплаченные, потом нет, сортировка по рейтингу продавца и дате)");
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementsDTO(
            a.id,
            a.title,
            a.category,
            a.price,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM Advertisement a
        JOIN a.seller u
        WHERE a.byeStatus = true
        AND a.status = 'ACTIVE'
        ORDER BY
             COALESCE((SELECT AVG(s.score) FROM Score s WHERE s.seller = u), 0.0) DESC,
             a.publicationDate DESC
        UNION ALL
        SELECT new AdvertisementsDTO(
            a.id,
            a.title,
            a.category,
            a.price,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM Advertisement a
        JOIN a.seller u
        WHERE a.byeStatus = false
        AND a.status = 'ACTIVE'
        ORDER BY
             COALESCE((SELECT AVG(s.score) FROM Score s WHERE s.seller = u), 0.0) DESC,
             a.publicationDate DESC
    """;

        Query<AdvertisementsDTO> query = session.createQuery(hql, AdvertisementsDTO.class);
        return query.getResultList();
    }

    public AdvertisementItemDTO findAdvertisementItem(int id) {
        logger.debug("Показ объявления с id {}", id);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementItemDTO(
            a.id,
            a.title,
            a.category,
            a.description,
            a.price,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM Advertisement a
        JOIN a.seller u
        WHERE a.id = :id
    """;

        Query<AdvertisementItemDTO> query = session.createQuery(hql, AdvertisementItemDTO.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public AdvertisementItemUsersDTO findAdvertisementUsersItem(int id) {
        logger.debug("Показ объявления с id {} для продавца", id);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementItemUsersDTO(
            a.id,
            a.title,
            a.category,
            a.description,
            a.price,
            a.byestatus
        )
        FROM Advertisement a
        WHERE a.id = :id
    """;

        Query<AdvertisementItemUsersDTO> query = session.createQuery(hql, AdvertisementItemUsersDTO.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public List<AdvertisementsUsersDTO> findAdvertisementsUsers(int idSeller) {
        logger.debug("Показ объявлений продавца с id {}", idSeller);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementsUsersDTO(
            a.id,
            a.title,
            a.category,
            a.price
        )
        FROM Advertisement a
        WHERE a.idseller = :idseller
    """;

        Query<AdvertisementsUsersDTO> query = session.createQuery(hql, AdvertisementsUsersDTO.class);
        query.setParameter("idseller", idSeller);
        return query.getResultList();
    }

    public List<AdvertisementsDTO> findAdvertisementWithCategory (ProductsCategory category){
        logger.debug("Поиск всех объявлений с информацией о продавце (сначала проплаченные, потом нет, сортировка по рейтингу продавца и дате)");
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementsDTO(
            a.id,
            a.title,
            a.category,
            a.price,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM Advertisement a
        JOIN a.seller u
        WHERE a.byeStatus = true
        AND a.status = 'ACTIVE'
        AND a.category = :category
        ORDER BY
             COALESCE((SELECT AVG(s.score) FROM Score s WHERE s.seller = u), 0.0) DESC,
             a.publicationDate DESC
        UNION ALL
        SELECT new AdvertisementsDTO(
            a.id,
            a.title,
            a.category,
            a.price,
            u.login,
            COALESCE(
                (SELECT AVG(s.score) FROM Score s WHERE s.seller = u),
                0.0
            )
        )
        FROM Advertisement a
        JOIN a.seller u
        WHERE a.byeStatus = false
        AND a.status = 'ACTIVE'
        AND a.category = :category
        ORDER BY
             COALESCE((SELECT AVG(s.score) FROM Score s WHERE s.seller = u), 0.0) DESC,
             a.publicationDate DESC
    """;

        Query<AdvertisementsDTO> query = session.createQuery(hql, AdvertisementsDTO.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}