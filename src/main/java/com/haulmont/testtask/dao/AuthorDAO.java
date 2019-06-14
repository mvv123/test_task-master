package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.base.Author;

import java.util.List;

/**
 * Интерфейс для описания методов доступа к бд для сущности {@link Author}
 */
public interface AuthorDAO {

    void insert(Author author);
    void delete(Author author);
    void update(Author author);
    Author find(Long id);
    List<Author> findAll();

}