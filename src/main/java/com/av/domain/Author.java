package com.av.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.ManyToMany;
import java.util.List;


@Table(name = "authors")
@Entity
@NamedQueries(
        {@NamedQuery(name = Author.FIND_ALL, query = "select a from Author a"),
                @NamedQuery(name = Author.FIND_BY_NAME, query = "select a from Author a where a.name = :name")

        }
)
public class Author {

    public static final String FIND_ALL = "Author.findAll";
    public static final String FIND_BY_NAME = "Author.byName";
    private long id;
    private String name;
    private List<Book> books;

    public Author() {

    }

    public Author(String name) {

        this();
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s'}", id, name);
    }


    @ManyToMany(targetEntity = Book.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "book_fk"))
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
