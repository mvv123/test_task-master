package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.dictionary.DictPublisher;

import java.util.List;

/**
 * Интерфейс для описания методов доступа к бд для сущности {@link DictPublisher}
 */
public interface DictPublisherDAO {

    DictPublisher find(Long id);
    List<DictPublisher> findAll();

}