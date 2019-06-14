package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.base.Genre;

import java.util.List;

/**
 * Интерфейс для описания методов доступа к бд для сущности {@link Genre}
 */
public interface GenreDAO {

    void insert(Genre genre);
    void delete(Genre genre);
    void update(Genre genre);
    Genre find(Long id);
    List<Genre> findAll();

}