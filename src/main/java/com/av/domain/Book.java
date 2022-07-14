package com.av.domain;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"title", "edition"}, name = "book_title_edition_u1")})
@NamedQueries({@NamedQuery(name = Book.FIND_ALL, query = "select  distinct  b from Book b "
        + " left join fetch b.authors a "
        + " left join fetch b.genre g")})

public class Book {

    public static final String FIND_ALL = "Book.findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = Genre.class, fetch = FetchType.EAGER)
    private Genre genre;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "book_author_t", joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "book_author_key"),
            name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    // @JoinColumn(foreignKey=@ForeignKey(name="book_author_key"))
    private Set<Author> authors;
    @Column(name = "title", length = 255, nullable = false)
    private String title;
    @Column(name = "publish_year", nullable = true)
    private int publishYear;
    @Column(name = "isbn", length = 50, nullable = false)
    private String isbn;
    @Column(name = "edition", length = 1)
    private short edition = 1;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "book_id")
    private List<Comment> comments = new ArrayList<Comment>() ;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public Book() {
        authors = new HashSet<Author>();
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public short getEdition() {
        return edition;
    }

    public void setEdition(short edition) {
        this.edition = edition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", genre=" + genre +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", publishYear=" + publishYear +
                ", isbn='" + isbn + '\'' +
                ", edition=" + edition +
                ", comments=" + comments +
                '}';
    }
}
