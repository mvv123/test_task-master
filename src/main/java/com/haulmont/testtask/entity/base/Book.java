package com.haulmont.testtask.entity.base;

import com.haulmont.testtask.entity.dictionary.DictPublisher;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE , generator = "book_gen")
    @SequenceGenerator(name = "book_gen", sequenceName = "seq_book_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "year")
    private Integer year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)

    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", nullable = false)
    private DictPublisher publisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public DictPublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(DictPublisher publisher) {
        this.publisher = publisher;
    }

    public String getAuthorFIO() {
        return author.getAuthorFIO();
    }

    public String getPublisherName() {
        return publisher.getName();
    }

    public String getGenreName() {
        return genre.getName();
    }

}