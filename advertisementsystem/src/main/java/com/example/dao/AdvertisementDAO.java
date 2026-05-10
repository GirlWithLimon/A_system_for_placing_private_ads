package com.example.dao;

import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.model.Advertisement;
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
        logger.debug("Поиск всех объявлений с информацией о продавце (сначала проплаченные, потом нет)");
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementsDTO(
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
        UNION ALL
        SELECT new AdvertisementsDTO(
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
    """;

        Query<AdvertisementsDTO> query = session.createQuery(hql, AdvertisementsDTO.class);
        return query.getResultList();
    }

    public AdvertisementItemDTO findAdvertisementItem(int id) {
        logger.debug("Показ объявления с id {}", id);
        Session session = getCurrentSession();

        String hql = """
        SELECT new com.example.dto.AdvertisementItemDTO(
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
}