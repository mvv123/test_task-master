package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.dictionary.DictPublisher;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Реализация методов доступа к бд для сущности {@link DictPublisher}
 */
public class DictPublisherDAOImpl implements DictPublisherDAO {

    @Override
    public DictPublisher find(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        DictPublisher publisher = session.get(DictPublisher.class, id);
        session.close();
        return publisher;
    }

    @Override
    public List<DictPublisher> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DictPublisher.class);
        criteria.addOrder(Order.desc("id"));
        List<DictPublisher> list = criteria.list();
        session.close();
        return list;
    }

}