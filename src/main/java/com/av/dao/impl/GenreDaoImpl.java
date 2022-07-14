package com.av.dao.impl;

import com.av.dao.GenreDao;
import com.av.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext
    private final EntityManager entityManager;

    public GenreDaoImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Genre save(Genre genre) {


        if (Objects.isNull(genre.getId())) {

            entityManager.persist(genre);
        } else {
            entityManager.merge(genre);
        }
        return genre;

    }

    @Override
    public void delete(Genre genre) {

        var mergedEntity = entityManager.merge(genre);
        entityManager.remove(mergedEntity);

    }

    @Override
    public List<Genre> getAll() {
        return
                entityManager.createNamedQuery(Genre.FIND_ALL, Genre.class).getResultList();
    }

    @Override
    public Genre findByName(String genreName) {
        var query = entityManager.createNamedQuery(Genre.FIND_BY_NAME, Genre.class);
        query.setParameter("name", genreName);
        return query.getSingleResult();

    }
}
