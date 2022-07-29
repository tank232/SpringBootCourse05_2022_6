package com.av.cli;

import com.av.dao.AuthorDao;
import com.av.domain.Author;
import java.text.MessageFormat;

import com.av.services.ObjectFormatter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;

@ShellComponent
@Slf4j
public class AuthorDefinerCommand {

    private final AuthorDao authorDao;

    public AuthorDefinerCommand(AuthorDao authorDao, ObjectFormatter formatter) {
        this.authorDao = authorDao;

    }

    @Transactional
    @ShellMethod("set locale fo session")
    public void addAuthor(String name) {
        log.debug(String.format("addAuthor with name:%s", name));
        var author = authorDao.save(new Author(name));
        log.debug("new AuthorId=" + author.getId());
    }

    @ShellMethod("show authors")
    public void showAuthors() {
        log.info("ho ho");
        var authorList = authorDao.getAll();

        if (authorList.size() != 0) {
           authorDao.getAll().forEach(a -> log.info(MessageFormat.format("author:{0}", a.toString())));
        } else {
            log.debug("No author in db");
        }
    }
}
