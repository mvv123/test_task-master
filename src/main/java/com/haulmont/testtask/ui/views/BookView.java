package com.haulmont.testtask.ui.views;

import com.haulmont.testtask.entity.base.Book;
import com.haulmont.testtask.entity.dictionary.DictPublisher;
import com.haulmont.testtask.services.BookService;
import com.haulmont.testtask.services.DictPublisherService;
import com.haulmont.testtask.services.PropertiesService;
import com.haulmont.testtask.ui.CustomizeVerticalLayout;
import com.haulmont.testtask.ui.cards.BookCard;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Properties;
import java.util.Set;

public class BookView extends CustomizeVerticalLayout implements View {

    public static final String NAME = "Книги";
    public static final String URL = "book";

    private BookService bookService;

    private DictPublisherService dictPublisherService;

    private ListDataProvider<Book> dataProvider;

    private Properties bookProperties;

    private Grid<Book> grid;

    public BookView() {
        bookService = BookService.getInstance();
        dictPublisherService = DictPublisherService.getInstance();

        bookProperties = PropertiesService.getInstance().getBookProperties();

        createMenu();

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button create = new Button(bookProperties.getProperty("button.create") , clickEvent -> {
            BookCard bookCard = new BookCard();
            bookCard.addCloseListener(closeEvent ->  {
                dataProvider = new ListDataProvider<>(bookService.findAllBooks());
                grid.setDataProvider(dataProvider);
            });
            UI.getCurrent().addWindow(bookCard);
        });
        create.setVisible(true);
        buttonsLayout.addComponent(create);

        Button edit = new Button(bookProperties.getProperty("button.edit") , clickEvent -> {
            BookCard bookCard = new BookCard(grid.getSelectedItems().iterator().next());
            bookCard.addCloseListener(closeEvent -> {
                dataProvider = new ListDataProvider<>(bookService.findAllBooks());
                grid.setDataProvider(dataProvider);
            });
            UI.getCurrent().addWindow(bookCard);
        });
        edit.setVisible(false);
        buttonsLayout.addComponent(edit);

        Button delete = new Button(bookProperties.getProperty("button.delete") , clickEvent -> {
            for(Book book : grid.getSelectedItems()) {
                bookService.deleteBook(book);
            }
            grid.setItems(bookService.findAllBooks());
        });
        delete.setVisible(false);
        buttonsLayout.addComponent(delete);

        addComponent(buttonsLayout);

        HorizontalLayout filtersLayout = new HorizontalLayout();

        TextField filterName = new TextField(bookProperties.getProperty("filter.name"));
        filtersLayout.addComponent(filterName);

        TextField filterAuthor = new TextField(bookProperties.getProperty("filter.author"));
        filtersLayout.addComponent(filterAuthor);

        ComboBox<DictPublisher> filterPublisher = new ComboBox<>(bookProperties.getProperty("filter.publisher"));
        filterPublisher.setItems(dictPublisherService.findAllPublishers());
        filterPublisher.setItemCaptionGenerator(DictPublisher::getName);

        filtersLayout.addComponent(filterPublisher);

        addComponent(filtersLayout);

        dataProvider = new ListDataProvider<>(bookService.findAllBooks());

        Button apply = new Button(bookProperties.getProperty("button.apply") , clickEvent -> {
            dataProvider.clearFilters();
            if(filterName.getValue() != null)
                dataProvider.addFilter(book -> (book.getName().indexOf(filterName.getValue()) != -1));
            if(filterAuthor.getValue() != null)
                dataProvider.addFilter(book -> (book.getAuthorFIO().indexOf(filterAuthor.getValue()) != -1));
            if(filterPublisher.getValue() != null)
                dataProvider.addFilter(book -> book.getPublisher().getName().equals(filterPublisher.getValue().getName()));
        });

        addComponent(apply);

        grid = new Grid<>();
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Book::getName).setCaption(bookProperties.getProperty("column.name")).setExpandRatio(1);
        grid.addColumn(Book::getYear).setCaption(bookProperties.getProperty("column.year")).setExpandRatio(1);
        grid.addColumn(Book::getCity).setCaption(bookProperties.getProperty("column.city")).setExpandRatio(1);
        grid.addColumn(Book::getAuthorFIO).setCaption(bookProperties.getProperty("column.author")).setExpandRatio(1);
        grid.addColumn(Book::getPublisherName).setCaption(bookProperties.getProperty("column.publisher")).setExpandRatio(1);
        grid.addColumn(Book::getGenreName).setCaption(bookProperties.getProperty("column.genre")).setExpandRatio(1);

        grid.addSelectionListener(event -> {
            Set<Book> selected = event.getAllSelectedItems();

            edit.setVisible(selected.size() == 1);
            delete.setVisible(selected.size() > 0);
        });

        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}

}