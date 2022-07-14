package com.av.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name = "genre")
@NamedQueries({
        @NamedQuery(name = Genre.FIND_ALL , query = "select g from Genre g"),
        @NamedQuery(name = Genre.FIND_BY_NAME , query = "select g from Genre g where g.name = :name")
})
public class Genre {


    private long id;
    private String name;

    public static final String FIND_ALL = "Genre.findAll";
    public static final String FIND_BY_NAME = "Genre.findByName";

    public Genre() {
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
        return "Genre{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
