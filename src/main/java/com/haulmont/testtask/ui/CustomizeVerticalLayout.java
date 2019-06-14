package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.views.AuthorView;
import com.haulmont.testtask.ui.views.BookView;
import com.haulmont.testtask.ui.views.GenreView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CustomizeVerticalLayout extends VerticalLayout {

    public CustomizeVerticalLayout() {
        setMargin(true);
    }

    protected void createMenu() {
        MenuBar menu = new MenuBar();

        menu.addItem(AuthorView.NAME , menuItem -> UI.getCurrent().getNavigator().navigateTo(AuthorView.URL));

        menu.addItem(BookView.NAME , menuItem -> UI.getCurrent().getNavigator().navigateTo(BookView.URL));

        menu.addItem(GenreView.NAME , menuItem -> UI.getCurrent().getNavigator().navigateTo(GenreView.URL));

        addComponent(menu);
    }
}