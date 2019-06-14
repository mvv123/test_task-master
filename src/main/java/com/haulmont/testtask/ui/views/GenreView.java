package com.haulmont.testtask.ui.views;

import com.haulmont.testtask.entity.base.Genre;
import com.haulmont.testtask.services.GenreService;
import com.haulmont.testtask.services.PropertiesService;
import com.haulmont.testtask.ui.CustomizeVerticalLayout;
import com.haulmont.testtask.ui.cards.GenreCard;
import com.haulmont.testtask.ui.cards.GenreStatistic;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Properties;
import java.util.Set;

public class GenreView extends CustomizeVerticalLayout implements View {

    public static final String NAME = "Жанры";
    public static final String URL = "genre";

    private GenreService genreService;

    private Properties genreProperties;

    private Grid<Genre> grid;

    public GenreView() {
        genreService = GenreService.getInstance();

        genreProperties = PropertiesService.getInstance().getGenreProperties();

        createMenu();

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button create = new Button(genreProperties.getProperty("button.create") , clickEvent -> {
            GenreCard genreCard = new GenreCard();
            genreCard.addCloseListener(closeEvent -> grid.setItems(genreService.findAllGenres()));
            UI.getCurrent().addWindow(genreCard);
        });
        create.setVisible(true);
        buttonsLayout.addComponent(create);

        Button edit = new Button(genreProperties.getProperty("button.edit") , clickEvent -> {
            GenreCard genreCard = new GenreCard(grid.getSelectedItems().iterator().next());
            genreCard.addCloseListener(closeEvent -> grid.setItems(genreService.findAllGenres()));
            UI.getCurrent().addWindow(genreCard);
        });
        edit.setVisible(false);
        buttonsLayout.addComponent(edit);

        Button delete = new Button(genreProperties.getProperty("button.delete") , clickEvent -> {
            for(Genre genre : grid.getSelectedItems()) {
                if(genre.getBooks().size() == 0) genreService.deleteGenre(genre);
                else Notification.show(genreProperties.getProperty("error.delete") + genre.getName() , Notification.Type.ERROR_MESSAGE);
            }
            grid.setItems(genreService.findAllGenres());
        });
        delete.setVisible(false);
        buttonsLayout.addComponent(delete);

        Button statistic = new Button(genreProperties.getProperty("button.stats") , clickEvent -> {
            GenreStatistic genreStatistic = new GenreStatistic();
            UI.getCurrent().addWindow(genreStatistic);
        });
        statistic.setVisible(true);
        buttonsLayout.addComponent(statistic);

        addComponent(buttonsLayout);

        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(genreService.findAllGenres());
        grid.addColumn(Genre::getName).setCaption(genreProperties.getProperty("column.name")).setExpandRatio(1);

        grid.addSelectionListener(event -> {
            Set<Genre> selected = event.getAllSelectedItems();

            edit.setVisible(selected.size() == 1);
            delete.setVisible(selected.size() > 0);
        });

        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}

}