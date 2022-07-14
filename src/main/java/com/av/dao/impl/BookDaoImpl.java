package com.av.dao.impl;

import com.av.dao.BookDao;
import com.av.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class BookDaoImpl implements BookDao<Long, Book> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        return entityManager.createNamedQuery(Book.FIND_ALL, Book.class).getResultList();
    }

}
