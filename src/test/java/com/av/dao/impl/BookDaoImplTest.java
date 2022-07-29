package com.av.dao.impl;

import com.av.dao.BookDao;
import com.av.domain.Book;
import com.av.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
@Import(BookDaoImpl.class)
@ActiveProfiles("test")
class BookDaoImplTest {


    @Autowired
    BookDao bookDao;

    @Test
    void saveSimpleBook() {
        var book = new Book();
        book.setIsbn("isbn_dummy");
        book.setTitle("title dummy");
        book.setEdition((short) 1);
        bookDao.save(book);
        assertNotNull(book.getId(), "book nor added");
    }

    @Test
    void saveBookWithComments() {
        var book = new Book();
        book.setIsbn("isbn_dummy");
        book.setTitle("title dummy");
        book.setEdition((short) 1);

        var comment = new Comment();
        comment.setBook(book);
        comment.setUserName("dummy author");
        comment.setText("dummy text");
        book.setComments(Collections.singletonList(comment));


        bookDao.save(book);
        assertNotNull(book.getId(), "book nor added");

        book.getComments().forEach(com -> {
            assertNotNull(com.getId(), "comments not saved");
        });
    }

    @Test
    void delete() {
        var bookCollection = bookDao.getAll();
        assertEquals(1, bookCollection.size(), "book count not expected");
        var book = bookCollection.get(0);
        bookDao.delete(book);
        assertEquals(0, bookDao.getAll().size(), "book count not expected");
    }

    @Test
    void getAll() {
        assertEquals(1, bookDao.getAll().size(), "book count not expected");

        var book = new Book();
        book.setIsbn("isbn_dummy");
        book.setTitle("title dummy");
        book.setEdition((short) 1);
        bookDao.save(book);

        assertEquals(2, bookDao.getAll().size(), "book count not expected");


    }
}