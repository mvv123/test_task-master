package com.haulmont.testtask.ui.cards;

import com.haulmont.testtask.entity.base.Author;
import com.haulmont.testtask.entity.base.Book;
import com.haulmont.testtask.entity.base.Genre;
import com.haulmont.testtask.entity.dictionary.DictPublisher;
import com.haulmont.testtask.services.*;
import com.haulmont.testtask.ui.CustomizeConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Calendar;
import java.util.Properties;

/**
 * Интерфейс-карточка в виде модального окна, отображающая интерфейс
 * создания и редактирования, для сущности {@link Book}
 */
public class BookCard extends Window {

    private Book book;

    private BookService bookService;

    private AuthorService authorService;

    private GenreService genreService;

    private DictPublisherService dictPublisherService;

    private Properties bookProperties;

    public BookCard() {
        createForm();
    }

    public BookCard(Book book) {
        this.book = book;
        createForm();
    }

    private void createForm() {
        bookService = BookService.getInstance();
        authorService = AuthorService.getInstance();
        genreService = GenreService.getInstance();
        dictPublisherService = DictPublisherService.getInstance();

        bookProperties = PropertiesService.getInstance().getBookProperties();

        setModal(true);
        setResizable(false);
        setDraggable(false);

        Binder<Book> binder = new Binder<>();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);

        TextField name = new TextField(bookProperties.getProperty("column.name"));
        name.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(name);

        binder.forField(name)
                .asRequired(bookProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я ]+") , bookProperties.getProperty("validator.charsname"))
                .withValidator(new StringLengthValidator(bookProperties.getProperty("validator.length") , 2 , 30))
                .bind(Book::getName , Book::setName);

        ComboBox<Author> authorComboBox = new ComboBox<>(bookProperties.getProperty("column.author"));
        authorComboBox.setItems(authorService.findAllAuthors());
        authorComboBox.setRequiredIndicatorVisible(true);
        authorComboBox.setItemCaptionGenerator(Author::getAuthorFIO);
        verticalLayout.addComponent(authorComboBox);

        binder.forField(authorComboBox)
                .asRequired(bookProperties.getProperty("validator.require"))
                .bind(Book::getAuthor , Book::setAuthor);

        ComboBox<Genre> genreComboBox = new ComboBox<>(bookProperties.getProperty("column.genre"));
        genreComboBox.setItems(genreService.findAllGenres());
        genreComboBox.setRequiredIndicatorVisible(true);
        genreComboBox.setItemCaptionGenerator(Genre::getName);
        verticalLayout.addComponent(genreComboBox);

        binder.forField(genreComboBox)
                .asRequired(bookProperties.getProperty("validator.require"))
                .bind(Book::getGenre , Book::setGenre);

        ComboBox<DictPublisher> dictPublisherComboBox = new ComboBox<>(bookProperties.getProperty("column.publisher"));
        dictPublisherComboBox.setItems(dictPublisherService.findAllPublishers());
        dictPublisherComboBox.setRequiredIndicatorVisible(true);
        dictPublisherComboBox.setItemCaptionGenerator(DictPublisher::getName);
        verticalLayout.addComponent(dictPublisherComboBox);

        binder.forField(dictPublisherComboBox)
                .asRequired(bookProperties.getProperty("validator.require"))
                .bind(Book::getPublisher , Book::setPublisher);

        TextField year = new TextField(bookProperties.getProperty("column.year"));
        year.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(year);

        binder.forField(year)
                .asRequired(bookProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[0-9]+") , bookProperties.getProperty("validator.int"))
                .withValidator(value -> value.length() == 4 , bookProperties.getProperty("validator.lengthyear"))
                .withValidator(value -> Integer.valueOf(value) <= Calendar.getInstance().get(Calendar.YEAR), bookProperties.getProperty("validator.year"))
                .withConverter(new CustomizeConverter())
                .bind(Book::getYear , Book::setYear);

        TextField city = new TextField(bookProperties.getProperty("column.city"));
        city.setRequiredIndicatorVisible(true);
        verticalLayout.addComponent(city);

        binder.forField(city)
                .asRequired(bookProperties.getProperty("validator.require"))
                .withValidator(value -> value.matches("[A-Za-zА-Яа-я-]+") , bookProperties.getProperty("validator.charscity"))
                .withValidator(new StringLengthValidator(bookProperties.getProperty("validator.lengthcity") , 2 , 50))
                .bind(Book::getCity , Book::setCity);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button close = new Button(bookProperties.getProperty("button.cancel"));
        close.addClickListener(clickEvent -> this.close());

        horizontalLayout.addComponent(close);

        Button accept = new Button(bookProperties.getProperty("button.ok"));

        if(book == null) {
            book = new Book();

            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    bookService.insertBook(book);
                    this.close();
                }
            });
        } else {
            accept.addClickListener(clickEvent -> {
                if(binder.validate().isOk()) {
                    bookService.updateBook(book);
                    this.close();
                }
            });
        }

        horizontalLayout.addComponent(accept);

        binder.setBean(book);

        verticalLayout.addComponent(horizontalLayout);

        setContent(verticalLayout);
    }

}