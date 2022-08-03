package com.av.dao.impl;

import com.av.dao.BookDao;
import com.av.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Repository

public class BookDaoImpl implements BookDao {


    private final EntityManager entityManager;

    public BookDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book save(Book book) {

        if (Objects.isNull(book.getId())) {
            entityManager.persist(book);
        } else {
            entityManager.merge(book);
        }
        return book;
    }

    @Override
    public void delete(Book book) {
        var mergedEntity = entityManager.merge(book);
        entityManager.remove(mergedEntity);
    }


    @Override
    public List<Book> getAll() {
        EntityGraph<Book> entityGraph = (EntityGraph<Book>) entityManager.getEntityGraph("avatars-entity-graph");
        TypedQuery<Book> namedQuery = entityManager.createNamedQuery(Book.FIND_ALL, Book.class);
        namedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        return namedQuery.getResultList();
    }


    @Override
    public Book findByName(String title) {
        var query = entityManager.createNamedQuery(Book.FIND_BY_NAME, Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }


}




