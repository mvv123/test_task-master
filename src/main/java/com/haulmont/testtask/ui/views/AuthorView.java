package com.haulmont.testtask.ui.views;

import com.haulmont.testtask.entity.base.Author;
import com.haulmont.testtask.services.AuthorService;
import com.haulmont.testtask.services.PropertiesService;
import com.haulmont.testtask.ui.CustomizeVerticalLayout;
import com.haulmont.testtask.ui.cards.AuthorCard;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Properties;
import java.util.Set;

public class AuthorView extends CustomizeVerticalLayout implements View {

    public static final String NAME = "Авторы";
    public static final String URL = "author";

    private AuthorService authorService;

    private Grid<Author> grid;

    private Properties authorProperties;

    public AuthorView() {
        authorService = AuthorService.getInstance();

        authorProperties = PropertiesService.getInstance().getAuthorProperties();

        createMenu();

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button create = new Button(authorProperties.getProperty("button.create") , clickEvent -> {
            AuthorCard authorCard = new AuthorCard();
            authorCard.addCloseListener(closeEvent -> grid.setItems(authorService.findAllAuthors()));
            UI.getCurrent().addWindow(authorCard);
        });
        create.setVisible(true);
        buttonsLayout.addComponent(create);

        Button edit = new Button(authorProperties.getProperty("button.edit") , clickEvent -> {
            AuthorCard authorCard = new AuthorCard(grid.getSelectedItems().iterator().next());
            authorCard.addCloseListener(closeEvent -> grid.setItems(authorService.findAllAuthors()));
            UI.getCurrent().addWindow(authorCard);
        });
        edit.setVisible(false);
        buttonsLayout.addComponent(edit);

        Button delete = new Button(authorProperties.getProperty("button.delete") , clickEvent -> {
            for(Author author : grid.getSelectedItems()) {
                if(author.getBooks().size() == 0) authorService.deleteAuthor(author);
                else Notification.show(authorProperties.getProperty("error.delete") + author.getAuthorFIO() , Notification.Type.ERROR_MESSAGE);
            }
            grid.setItems(authorService.findAllAuthors());
        });
        delete.setVisible(false);
        buttonsLayout.addComponent(delete);
        addComponent(buttonsLayout);

        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(authorService.findAllAuthors());
        grid.addColumn(Author::getFirstName).setCaption(authorProperties.getProperty("column.firstname")).setExpandRatio(1);
        grid.addColumn(Author::getMiddleName).setCaption(authorProperties.getProperty("column.middlename")).setExpandRatio(1);
        grid.addColumn(Author::getLastName).setCaption(authorProperties.getProperty("column.lastname")).setExpandRatio(1);

        grid.addSelectionListener(event -> {
            Set<Author> selected = event.getAllSelectedItems();

            edit.setVisible(selected.size() == 1);
            delete.setVisible(selected.size() > 0);
        });

        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}

}