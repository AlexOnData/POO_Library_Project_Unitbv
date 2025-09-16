package com.example.service;
import com.example.dao.BookDao;
import com.example.entity.Book;

import javax.persistence.Persistence;
import java.util.List;

public class BookService {
    private BookDao bookDao;

    public BookService() {
        try {
            bookDao = new BookDao(Persistence.createEntityManagerFactory("persistence"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void addBook(Book newBook) {
        bookDao.create(newBook);
    }

    public void updateBook(Book updatedBook) {
        bookDao.update(updatedBook);
    }

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    // for login
    public List<Book> findBook(String author) throws Exception {
        List<Book> books = bookDao.find(author);
        if (books.isEmpty()) {
            throw new Exception("Book not found!");
        }
//        Book c = books.getFirst();
        return books;
    }
}
