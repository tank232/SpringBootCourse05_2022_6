package com.av.domain;

import lombok.Data;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
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
@NamedQueries({
        @NamedQuery(name = Book.FIND_ALL, query = "select    b from Book b"),
        @NamedQuery(name = Book.FIND_BY_NAME, query = "select    b from Book b  where b.title = :title")
})
@NamedEntityGraph(name = "avatars-entity-graph",
        attributeNodes = {@NamedAttributeNode("title")})
@Data
public class Book {
    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_BY_NAME= "Book.byName";
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "book_author_t", joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "book_author_key"),
            name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", publishYear=" + publishYear +
                ", isbn='" + isbn + '\'' +
                ", edition=" + edition +
                ", comments=" + comments +
                '}';
    }
}
