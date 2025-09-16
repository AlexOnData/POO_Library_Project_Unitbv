package com.example.controller;

import com.example.entity.Book;
import com.example.service.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddBook {
    // These fields are linked to the input fields from the FXML interface (Scene Builder)
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField bookNameTextField;
    @FXML
    private TextField bookTypeTextField;

    // This method is triggered when the user clicks the "Add Book" button in the UI
    @FXML
    private void addBook() {
        // Create an instance of the service responsible for book operations
        BookService bookService = new BookService();

        // Create a new Book object and set its values from the text fields
        Book book = new Book();
        book.setAuthor(authorTextField.getText());
        book.setName(bookNameTextField.getText());
        book.setType(bookTypeTextField.getText());
        book.setStatus("Free");

        // Call the service method to save the book (delegating the logic to BookService)
        bookService.addBook(book);
    }
}
