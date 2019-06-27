package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.DictPublisherDAOImpl;
import com.haulmont.testtask.entity.dictionary.DictPublisher;

import java.util.List;

/**
 * Сервис выполняющий CRUD-функции для сущности {@link DictPublisher}
 */
public class DictPublisherService {

    private static DictPublisherService instance;

    private DictPublisherDAOImpl publisherDAO;

    private DictPublisherService() {
        publisherDAO = new DictPublisherDAOImpl();
    }

    public DictPublisher findPublisher(Long id) {
        return publisherDAO.find(id);
    }

    public List<DictPublisher> findAllPublishers() {
        return publisherDAO.findAll();
    }

    public static DictPublisherService getInstance() {
        if(instance == null) instance = new DictPublisherService();
        return instance;
    }
}