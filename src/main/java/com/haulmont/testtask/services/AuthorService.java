package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.AuthorDAOImpl;
import com.haulmont.testtask.entity.base.Author;

import java.util.List;

/**
 * Сервис выполняющий CRUD-функции для сущности {@link Author}
 */
public class AuthorService {

    private static AuthorService instance;

    private AuthorDAOImpl authorDAO;

    public AuthorService() {
        authorDAO = new AuthorDAOImpl();
    }

    public void insertAuthor(Author author) {
        authorDAO.insert(author);
    }

    public void deleteAuthor(Author author) {
        authorDAO.delete(author);
    }

    public void updateAuthor(Author author) {
        authorDAO.update(author);
    }

    public Author findAuthor(Long id) {
        return authorDAO.find(id);
    }

    public List<Author> findAllAuthors() {
        return authorDAO.findAll();
    }

    public static AuthorService getInstance() {
        if(instance == null) instance = new AuthorService();
        return instance;
    }

}