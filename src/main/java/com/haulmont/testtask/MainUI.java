package com.haulmont.testtask;

import com.haulmont.testtask.services.PropertiesService;
import com.haulmont.testtask.ui.views.AuthorView;
import com.haulmont.testtask.ui.views.BookView;
import com.haulmont.testtask.ui.views.GenreView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Properties;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private Properties mainProperties;

    public MainUI() {
        mainProperties = PropertiesService.getInstance().getMainProperties();
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(mainProperties.getProperty("title"));

        Navigator navigator = new Navigator(this , this);
        navigator.addView(AuthorView.URL , new AuthorView());
        navigator.addView(BookView.URL , new BookView());
        navigator.addView(GenreView.URL , new GenreView());

        navigator.navigateTo(AuthorView.URL);
    }
}