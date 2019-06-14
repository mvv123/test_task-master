package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.GenreDAOImpl;
import com.haulmont.testtask.entity.base.Genre;

import java.util.List;

/**
 * Сервис выполняющий CRUD-функции для сущности {@link Genre}
 */
public class GenreService {

    private static GenreService instance;

    private GenreDAOImpl genreDAO;

    public GenreService() {
        genreDAO = new GenreDAOImpl();
    }

    public void insertGenre(Genre genre) {
        genreDAO.insert(genre);
    }

    public void deleteGenre(Genre genre) {
        genreDAO.delete(genre);
    }

    public void updateGenre(Genre genre) {
        genreDAO.update(genre);
    }

    public Genre findGenre(Long id) {
        return genreDAO.find(id);
    }

    public List<Genre> findAllGenres() {
        return genreDAO.findAll();
    }

    public static GenreService getInstance() {
        if(instance == null) instance = new GenreService();
        return instance;
    }
}