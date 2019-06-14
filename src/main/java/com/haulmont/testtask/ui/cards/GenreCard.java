package com.haulmont.testtask.ui.cards;

import com.haulmont.testtask.entity.base.Genre;
import com.haulmont.testtask.services.GenreService;
import com.haulmont.testtask.services.PropertiesService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Properties;

/**
 * Интерфейс-карточка в виде модального окна, отображающая интерфейс
 * создания и редактирования, для сущности {@link Genre}
 */
public class GenreCard extends Window {

    private Genre genre;

    private GenreService genreService;

    private Properties genreProperties;

    public GenreCard() {
        createForm();
    }

    public GenreCard(Genre genre) {
        this.genre = genre;
        createForm();
    }

    private void createForm() {
        genreService = GenreService.getInstance();

        genreProperties = PropertiesService.getInstance().getGenreProperties();

        setModal(true);
        setResizable(false);
        setDraggable(false);

        Binder<Genre> binder = new Binder<>();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);

        TextField name = new TextField(genreProperties.getProperty("column.name"));
        name.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(name);

        binder.forField(name)
                .asRequired(genreProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я]+") , genreProperties.getProperty("validator.chars"))
                .withValidator(new StringLengthValidator(genreProperties.getProperty("validator.length") , 2 , 30))
                .bind(Genre::getName , Genre::setName);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button close = new Button(genreProperties.getProperty("button.cancel"));
        close.addClickListener(clickEvent -> this.close());

        horizontalLayout.addComponent(close);

        Button accept = new Button(genreProperties.getProperty("button.ok"));

        if(genre == null) {
            genre = new Genre();

            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    genreService.insertGenre(genre);
                    this.close();
                }
            });
        } else {
            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    genreService.updateGenre(genre);
                    this.close();
                }
            });
        }

        horizontalLayout.addComponent(accept);

        binder.setBean(genre);

        verticalLayout.addComponent(horizontalLayout);

        setContent(verticalLayout);
    }

}