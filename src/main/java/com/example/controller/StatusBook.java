package com.example.controller;

import com.example.entity.Reservation;
import com.example.entity.User;
import com.example.poo_project.Main;
import com.example.service.ReservationService;
import com.example.service.UserService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class StatusBook {
    @FXML private Label timeLeftField;
    @FXML private Button settings;

    private Timeline timeline;
    private ZonedDateTime dueAt; // when it should be returned

    @FXML
    private Button takeBookButton;

    // Called automatically when the FXML is loaded
    @FXML
    private void initialize() {
        if(!UserData.getCurrentUser().getUserName().equals("admin")) {
            settings.setVisible(false);
        }
        if(UserData.getLastBookTakenDate() != null) {
            startLoanTimer(UserData.getLastBookTakenDate());
        }
        else {
            showNoActiveLoans();
        }
    }

    public void takeBookOnAction() {
        UserService userService = new UserService();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ui-take-book.fxml"));
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
        Stage stage = (Stage) takeBookButton.getScene().getWindow();
        stage.close();
    }

    /** Call this right after the user takes the book */
    public void startLoanTimer(LocalDateTime takenAt) {
        // Due in 30 days from 'takenAt' (respect system timezone & DST)
        dueAt = takenAt.atZone(ZoneId.systemDefault()).plusDays(30);

        if (timeline != null) timeline.stop();

        // tick every second, on the JavaFX thread
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> updateCountdown()));
        timeline.setCycleCount(Animation.INDEFINITE);

        updateCountdown();   // update immediately so UI isn't blank for 1s
        timeline.play();
    }

    /** Optional: stop timer (e.g., on logout / close) */
    public void stopLoanTimer() {
        if (timeline != null) timeline.stop();
    }

    private void updateCountdown() {
        long seconds = ChronoUnit.SECONDS.between(ZonedDateTime.now(), dueAt);

        long abs = Math.abs(seconds);
        long days = abs / 86_400;
        long hours = (abs % 86_400) / 3_600;
        long minutes = (abs % 3_600) / 60;
        long secs = abs % 60;

        if (seconds >= 0) {
            timeLeftField.setText(String.format("%dd %02d:%02d:%02d left", days, hours, minutes, secs) + " for Book: " + UserData.getTakenBooks().getFirst().getName());
        } else {
            timeLeftField.setText(String.format("Overdue by %dd %02d:%02d:%02d", days, hours, minutes, secs) + " for Book: " + UserData.getTakenBooks().getFirst().getName());
        }
    }

    @FXML
    private void returnBook(){
        try {
            ReservationService service = new ReservationService();
            Reservation reservation = service.findReservation(UserData.getCurrentUser());
            if (reservation == null) {
                showNoActiveLoans();
            }
            else{
                reservation.setStatus("Returned");
                service.updateReservation(reservation);

                UserData.setLastBookTakenDate();
                startLoanTimer(UserData.getLastBookTakenDate());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void showNoActiveLoans() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        dueAt = null;
        timeLeftField.setText("No active loans."); // or "" to fully clear
    }

    public void openAdminPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-book.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            loginClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
