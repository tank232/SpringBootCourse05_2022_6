package com.av.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.av.dao.AuthorDao;
import com.av.domain.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(AuthorDaoImpl.class)
@ActiveProfiles("test")
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("author add")
    void add() {
        var author = new Author("fake");
        var a = authorDao.save(author);
        assertNotNull(a.getId(), "author nor added");
    }

    @Test
    void getAll() {
        var author = new Author("fake2");
        var a = authorDao.save(author);
        assertNotNull(a.getId(), "author nor added");

        var authorList = authorDao.getAll();

        assertEquals(2, authorList.size(), "author not fetched");
    }

    @Test
    void findByName() {
        var jonDaw = authorDao.findByName("Jon Daw");
        assertNotNull(jonDaw.getId());
    }

    @Test
    void update() {}

    @Test
    void delete() {
        var jonDaw = authorDao.findByName("Jon Daw");

        assertNotNull(jonDaw);

        authorDao.delete(jonDaw);
    }


}
