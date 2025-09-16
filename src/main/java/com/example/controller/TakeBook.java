package com.example.controller;

import com.example.entity.Book;
import com.example.entity.Reservation;
import com.example.poo_project.Main;
import com.example.service.BookService;
import com.example.service.ReservationService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TakeBook {

    @FXML
    private Button goBackButton;
    @FXML
    private ChoiceBox<String> bookAuthorChoiceBox;
    @FXML
    private ChoiceBox<String> bookNameChoiceBox;
    @FXML
    private ChoiceBox<String> bookTypeChoiceBox;

    private BookService bookService = new BookService();
    private List<Book> bookList = bookService.getAllBooks();

    @FXML
    private void initialize(){
        addAuthorChoiceBoxItems();

        bookAuthorChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldAuthor, newAuthor) -> {
            updateBooksForAuthor(newAuthor);
        });

        bookNameChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldAuthor, newBookName) -> {
            updateTypeForBook(newBookName);
        });
    }

    private void addAuthorChoiceBoxItems(){
        List<String> authors = new ArrayList<>();
        for(Book book : bookList){
            authors.add(book.getAuthor());
        }
        bookAuthorChoiceBox.setItems(FXCollections.observableArrayList(authors));

        // (Optional) set default value
//        bookAuthorChoiceBox.setValue(authors.get(0));
    }

    private void updateBooksForAuthor(String author) {
        if (author == null || author.isBlank()) {
            bookNameChoiceBox.getItems().clear();
            bookNameChoiceBox.setDisable(true);
            return;
        }

        bookNameChoiceBox.setValue(null);
        bookTypeChoiceBox.setValue(null);

        try {
            List<String> books = new ArrayList<>();
            for (Book b : bookService.findBook(author)) { // assuming this returns List<Book> for that author
                books.add(b.getName());
            }

            // update the **book names** ChoiceBox (fixed from your code)
            if (bookNameChoiceBox.getItems().isEmpty()) {
                bookNameChoiceBox.setItems(FXCollections.observableArrayList(books));
            } else {
                bookNameChoiceBox.getItems().setAll(books);
            }

            bookNameChoiceBox.getSelectionModel().clearSelection();
            bookNameChoiceBox.setDisable(books.isEmpty());

            // optional: pick first book by default
//            if (!books.isEmpty()) {
//                bookNameChoiceBox.setValue(books.get(0));
//            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            bookNameChoiceBox.getItems().clear();
            bookNameChoiceBox.setDisable(true);
        }
    }

    private void updateTypeForBook(String bookName) {
        for (Book b : bookList) {
            if (b.getName().equals(bookName)) {
//                bookTypeChoiceBox.setItems(FXCollections.observableArrayList(b.getType()));
//                bookTypeChoiceBox.getSelectionModel().clearSelection();
//                bookTypeChoiceBox.setDisable(b.getType().isEmpty());
                bookTypeChoiceBox.setValue(b.getType());
                return;
            }
        }
    }

    @FXML
    private void takeBook(){
        ReservationService service = new ReservationService();
        for (Book b : bookList) {
            if (b.getName().equals(bookNameChoiceBox.getValue()) && b.getAuthor().equals(bookAuthorChoiceBox.getValue()) && b.getType().equals(bookTypeChoiceBox.getValue())) {
                Reservation reservation = new Reservation();
                reservation.setBook(b);
                reservation.setReservationDate(LocalDateTime.now());
                reservation.setStatus("Reserved");
                reservation.setUser(UserData.getCurrentUser());
                service.addReservation(reservation);
                return;
            }
        }
        System.out.println("Book not found!");
    }

    @FXML
    private void goBack(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ui-status-book.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            loginClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void loginClose() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }
}
