package com.example.controller;

import com.example.entity.Book;
import com.example.entity.Reservation;
import com.example.entity.User;
import com.example.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

public class UserData {
    private static User currentUser;

    private static LocalDateTime lastBookTakenDate;

    private static List<Book> takenBooks;

    private UserData(){

    }

    public static void setCurrentUser(User user) throws Exception {
        currentUser = user;
        setLastBookTakenDate();
        setTakenBooks();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setLastBookTakenDate() throws Exception {
        ReservationService service = new ReservationService();

        Reservation reservation = service.findReservation(currentUser);
        if (reservation != null) {
            UserData.lastBookTakenDate = reservation.getReservationDate();
        }
        else {
            UserData.lastBookTakenDate = null;
        }

    }

    public static LocalDateTime getLastBookTakenDate() {
        return lastBookTakenDate;
    }

    private static void setTakenBooks() throws Exception {
        ReservationService service = new ReservationService();
        UserData.takenBooks = service.getTakenBooks(currentUser);
    }

    public static List<Book> getTakenBooks() {
        return takenBooks;
    }
}
