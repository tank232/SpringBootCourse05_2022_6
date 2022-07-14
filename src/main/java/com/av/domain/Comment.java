package com.av.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;
    @Column(name = "comment_data", length = 1000, nullable = false)
    private String text;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "book_fk"), name = "book_id")
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
