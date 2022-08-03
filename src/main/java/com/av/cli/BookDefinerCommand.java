package com.av.cli;

import com.av.dao.AuthorDao;
import com.av.dao.BookDao;
import com.av.domain.Book;
import com.av.domain.Comment;
import com.av.services.ObjectFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@Slf4j
public class BookDefinerCommand {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final ObjectFormatter formatter;


    public BookDefinerCommand(BookDao bookDao, AuthorDao authorDao, ObjectFormatter formatter) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.formatter = formatter;
    }

    @ShellMethod("show all books")
    public void listBooks() {
        log.info("ho ho");
        List<Book> books = bookDao.getAll();
        if (books.size() != 0) {
            books.forEach(book -> log.info(MessageFormat.format("Book:{0}", book.toString())));
        } else {
            log.debug("No author in db");
        }
    }

    @ShellMethod("add comment")
    @Transactional
    public void addComment(String bookTitle, String commentAuthor, String commentData) {
        Book book = bookDao.findByName(bookTitle);
        if (book != null) {
            var comment = new Comment();
            comment.setText(commentData);
            comment.setUserName(commentAuthor);
            comment.setBook(book);
            book.getComments().add(comment);
        } else {
            log.error("You mast init book first");
        }
    }


    @ShellMethod("show comment")
    @Transactional
    public void showComment(String bookTitle) {
        Book book = bookDao.findByName(bookTitle);
        if (book != null && book.getComments()!=null && book.getComments().size()>0) {
            log.info(MessageFormat.format("Book.comment:{0}",book.getComments().stream().map(b->b.getText()).collect(Collectors.joining(","))));
        } else {
            log.error("You mast init book & comment");
        }
    }


    @ShellMethod("create new book")
    @Transactional
    public void newBook(String title, short edition, String isbn) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setEdition(edition);
        newBook.setIsbn(isbn);
        bookDao.save(newBook);
        try {
            var data = formatter.format(newBook);
            log.info(data);
        } catch (JsonProcessingException e) {
            log.error("Ошибка форматирования", e);
        }
    }


    @ShellMethod("set author for new book")
    @Transactional
    public void setAuthorToNewBook(String bookTitle, String authorName) {
        Book book = bookDao.findByName(bookTitle);
        if (book != null) {
            var author = authorDao.findByName(authorName);

            if (author != null) {
                book.getAuthors().add(author);
                log.info(String.format("author by name=%s added to book", authorName));
            } else {
                log.error(String.format("author by name=%s not found", authorName));
            }
        } else {
            log.error("You mast init new book first");
        }
    }


}
