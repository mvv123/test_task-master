package com.haulmont.testtask.services;

import java.io.IOException;
import java.util.Properties;

/**
 * Сервис служит для получения и управления Properties
 */
public class PropertiesService {

    private static PropertiesService instance;

    private Properties mainProperties;

    private Properties authorProperties;

    private Properties bookProperties;

    private Properties genreProperties;

    private PropertiesService() {
        try {
            mainProperties = new Properties();
            mainProperties.load(getClass().getClassLoader().getResourceAsStream("properties/main.properties"));
            authorProperties = new Properties();
            authorProperties.load(getClass().getClassLoader().getResourceAsStream("properties/author.properties"));
            bookProperties = new Properties();
            bookProperties.load(getClass().getClassLoader().getResourceAsStream("properties/book.properties"));
            genreProperties = new Properties();
            genreProperties.load(getClass().getClassLoader().getResourceAsStream("properties/genre.properties"));
        } catch (IOException e) {}
    }

    public static PropertiesService getInstance() {
        if(instance == null) instance = new PropertiesService();
        return instance;
    }

    public Properties getMainProperties() {
        return mainProperties;
    }

    public Properties getAuthorProperties() {
        return authorProperties;
    }

    public Properties getBookProperties() {
        return bookProperties;
    }

    public Properties getGenreProperties() {
        return genreProperties;
    }
}