package com.haulmont.testtask.ui.cards;

import com.haulmont.testtask.entity.base.Author;
import com.haulmont.testtask.services.AuthorService;
import com.haulmont.testtask.services.PropertiesService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Properties;

/**
 * Интерфейс-карточка в виде модального окна, отображающая интерфейс
 * создания и редактирования, для сущности {@link Author}
 */
public class AuthorCard extends Window {

    private Author author;

    private AuthorService authorService;

    private Properties authorProperties;

    public AuthorCard() {
        createForm();
    }

    public AuthorCard(Author author) {
        this.author = author;
        createForm();
    }

    private void createForm() {
        authorService = AuthorService.getInstance();

        authorProperties = PropertiesService.getInstance().getAuthorProperties();

        setModal(true);
        setResizable(false);
        setDraggable(false);

        Binder<Author> binder = new Binder<>();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);

        TextField firstName = new TextField(authorProperties.getProperty("column.firstname"));
        firstName.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(firstName);

        binder.forField(firstName)
                .asRequired(authorProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я]+") , authorProperties.getProperty("validator.chars"))
                .withValidator(new StringLengthValidator(authorProperties.getProperty("validator.length") , 2 , 30))
                .bind(Author::getFirstName , Author::setFirstName);

        TextField middleName = new TextField(authorProperties.getProperty("column.middlename"));
        middleName.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(middleName);

        binder.forField(middleName)
                .asRequired(authorProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я]+") , authorProperties.getProperty("validator.chars"))
                .withValidator(new StringLengthValidator(authorProperties.getProperty("validator.length") , 2 , 30))
                .bind(Author::getMiddleName , Author::setMiddleName);

        TextField lastName = new TextField(authorProperties.getProperty("column.lastname"));
        lastName.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(lastName);

        binder.forField(lastName)
                .asRequired(authorProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я]+") , authorProperties.getProperty("validator.chars"))
                .withValidator(new StringLengthValidator(authorProperties.getProperty("validator.length") , 2 , 30))
                .bind(Author::getLastName , Author::setLastName);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button close = new Button(authorProperties.getProperty("button.cancel"));
        close.addClickListener(clickEvent -> this.close());

        horizontalLayout.addComponent(close);

        Button accept = new Button(authorProperties.getProperty("button.ok"));

        if(author == null) {
            author = new Author();

            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    authorService.insertAuthor(author);
                    this.close();
                }
            });
        } else {
            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    authorService.updateAuthor(author);
                    this.close();
                }
            });
        }

        horizontalLayout.addComponent(accept);

        binder.setBean(author);

        verticalLayout.addComponent(horizontalLayout);

        setContent(verticalLayout);
    }

}