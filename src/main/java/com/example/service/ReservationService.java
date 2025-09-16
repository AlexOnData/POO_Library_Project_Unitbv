package com.example.service;
import com.example.dao.ReservationDao;
import com.example.entity.Book;
import com.example.entity.Reservation;
import com.example.entity.User;

import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;

    public ReservationService() {
        try {
            reservationDao = new ReservationDao(Persistence.createEntityManagerFactory("persistence"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void addReservation(Reservation newReservation) {
        reservationDao.create(newReservation);
    }

    public void updateReservation(Reservation updatedReservation) {
        reservationDao.update(updatedReservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    /// for login
    public Reservation findReservation(User user) throws Exception {
        List<Reservation> reservations = reservationDao.find(user);
//        Reservation c = reservations.getFirst();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals("Reserved")) {
                return reservation;
            }
        }
        return null;
    }

    public List<Book> getTakenBooks(User user) throws Exception {
        List<Reservation> reservations = reservationDao.find(user);

        List<Book> takenBooks = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals("Reserved")) {
                takenBooks.add(reservation.getBook());
            }
        }
        return takenBooks;
    }

}
