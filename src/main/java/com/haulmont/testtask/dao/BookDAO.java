package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.base.Book;

import java.util.List;

/**
 * Интерфейс для описания методов доступа к бд для сущности {@link Book}
 */
public interface BookDAO {

    void insert(Book book);
    void delete(Book book);
    void update(Book book);
    Book find(Long id);
    List<Book> findAll();

}