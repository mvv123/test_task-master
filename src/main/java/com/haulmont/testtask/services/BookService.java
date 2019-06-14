package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.BookDAOImpl;
import com.haulmont.testtask.entity.base.Book;

import java.util.List;

/**
 * Сервис выполняющий CRUD-функции для сущности {@link Book}
 */
public class BookService {

    private static BookService instance;

    private BookDAOImpl bookDAO;

    public BookService() {
        bookDAO = new BookDAOImpl();
    }

    public void insertBook(Book book) {
        bookDAO.insert(book);
    }

    public void deleteBook(Book book) {
        bookDAO.delete(book);
    }

    public void updateBook(Book book) {
        bookDAO.update(book);
    }

    public Book findBook(Long id) {
        return bookDAO.find(id);
    }

    public List<Book> findAllBooks() {
        return bookDAO.findAll();
    }

    public static BookService getInstance() {
        if(instance == null) instance = new BookService();
        return instance;
    }
}