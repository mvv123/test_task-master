package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.base.Genre;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Реализация методов доступа к бд для сущности {@link Genre}
 */
public class GenreDAOImpl implements GenreDAO {

    @Override
    public void insert(Genre genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(genre);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Genre genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(genre);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Genre genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(genre);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Genre find(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Genre genre = session.get(Genre.class, id);
        session.close();
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Genre.class);
        criteria.addOrder(Order.desc("id"));
        List<Genre> list = criteria.list();
        session.close();
        return list;
    }

}