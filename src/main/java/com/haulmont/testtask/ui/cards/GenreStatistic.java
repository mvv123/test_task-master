package com.haulmont.testtask.ui.cards;

import com.haulmont.testtask.entity.base.Genre;
import com.haulmont.testtask.services.GenreService;
import com.haulmont.testtask.services.PropertiesService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.Properties;

/**
 * Интерфейс-карточка в виде модального окна, отображающая
 * статистическую информацию о количестве книг для каждого жанра
 */
public class GenreStatistic extends Window {

    private GenreService genreService;

    private Properties genreProperties;

    public GenreStatistic() {
        setModal(true);
        setResizable(false);
        setDraggable(false);

        genreService = GenreService.getInstance();

        genreProperties = PropertiesService.getInstance().getGenreProperties();

        VerticalLayout verticalLayout = new VerticalLayout();

        Grid<Genre> grid = new Grid<>();
        grid.setItems(genreService.findAllGenres());
        grid.addColumn(Genre::getName).setCaption(genreProperties.getProperty("column.name"));
        grid.addColumn(Genre::getBookCount).setCaption(genreProperties.getProperty("column.count"));

        verticalLayout.addComponent(grid);

        Button close = new Button(genreProperties.getProperty("button.close"));
        close.addClickListener(clickEvent -> this.close());

        verticalLayout.addComponent(close);

        setContent(verticalLayout);
    }

}