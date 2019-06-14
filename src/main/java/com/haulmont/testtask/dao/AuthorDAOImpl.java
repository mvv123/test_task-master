package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.base.Author;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Реализация методов доступа к бд для сущности {@link Author}
 */
public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public void insert(Author author) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Author author) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Author author) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Author find(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Author author = session.get(Author.class, id);
        session.close();
        return author;
    }

    @Override
    public List<Author> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Author.class);
        criteria.addOrder(Order.desc("id"));
        List<Author> list = criteria.list();
        session.close();
        return list;
    }

}