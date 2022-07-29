package com.av.dao.impl;

import com.av.dao.AuthorDao;
import com.av.domain.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private final EntityManager entityManager;


    public AuthorDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author save(Author author) {

        if (Objects.isNull(author.getId())) {

            entityManager.persist(author);
        } else {
            entityManager.merge(author);
        }
        return author;
    }

    @Override
    public void delete(Author author) {

        var mergedEntity = entityManager.merge(author);
        entityManager.remove(mergedEntity);

    }

    @Override
    public List<Author> getAll() {
        return entityManager.createNamedQuery(Author.FIND_ALL, Author.class).getResultList();

    }


    @Override
    public Author findByName(String authorName) {

        var query = entityManager.createNamedQuery(Author.FIND_BY_NAME, Author.class);
        query.setParameter("name", authorName);
        return query.getSingleResult();

    }
}
