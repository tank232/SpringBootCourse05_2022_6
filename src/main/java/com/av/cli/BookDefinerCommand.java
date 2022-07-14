package com.av.cli;

import com.av.dao.AuthorDao;
import com.av.dao.BookDao;
import com.av.dao.GenreDao;
import com.av.domain.Book;
import com.av.domain.Comment;
import com.av.services.ObjectFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;

@ShellComponent
public class BookDefinerCommand {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final ObjectFormatter formatter;
    private final GenreDao genreDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Book newBook = new Book();

    public BookDefinerCommand(BookDao bookDao, AuthorDao authorDao, ObjectFormatter formatter, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.formatter = formatter;
        this.genreDao = genreDao;
    }

    @ShellMethod("show all books")
    public void listBooks() {
        logger.info("ho ho");
        var books = bookDao.getAll();

        if (books.size() != 0) {
            books.forEach(book -> logger.info(MessageFormat.format("Book:{0}", book.toString())));
        } else {
            logger.debug("No author in db");
        }
    }

    @ShellMethod("show all books")
    public void addComment(String commentAuthor , String commentData ) {
        if (newBook != null) {

            var comment = new Comment();
            comment.setText(commentData);
            comment.setUserName(commentAuthor);
            comment.setBook(newBook);
            newBook.getComments().add(comment);

        } else {
            logger.error("You mast init new book first");
        }
    }

    @Transactional
    @ShellMethod("show books")
    public void showBooks() {
        logger.info("ho ho");
        var bookList = bookDao.getAll();

        if (bookList.size() != 0) {
            bookList.forEach(b -> {
                logger.info(b.toString());
            });

        } else {
            logger.debug("No book in db");
        }
    }

    @ShellMethod("create new book")
    public void newBook(String title, short edition, String isbn) {
        newBook = new Book();
        newBook.setTitle(title);
        newBook.setEdition(edition);
        newBook.setIsbn(isbn);

        try {
            var data = formatter.format(newBook);
            logger.info(data);
        } catch (JsonProcessingException e) {
            logger.error("Ошибка форматирования", e);
        }
    }

    @Transactional
    @ShellMethod("save new book")
    public void saveBook() {
        bookDao.save(newBook);
    }

    @ShellMethod("set author for new book")
    public void setAuthorToNewBook(String authorName) {
        if (newBook != null) {
            var author = authorDao.findByName(authorName);

            if (author != null) {
                newBook.getAuthors().add(author);
                logger.info(String.format("author by name=%s added to book", authorName));
            } else {
                logger.error(String.format("author by name=%s not found", authorName));
            }
        } else {
            logger.error("You mast init new book first");
        }
    }

    @ShellMethod("set author for new book")
    public void setGenreToNewBook(String genreName) {
        if (newBook != null) {
            var genre = genreDao.findByName(genreName);

            if (genre != null) {
                newBook.setGenre(genre);
                logger.info(String.format("genre by name=%s added to book", genre.getName()));
            } else {
                logger.error(String.format("author by name=%s not found", genreName));
            }
        } else {
            logger.error("You mast init new book first");
        }
    }
}
