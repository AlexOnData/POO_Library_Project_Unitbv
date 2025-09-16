package com.example.controller;

import com.example.entity.User;
import com.example.poo_project.Main;
import com.example.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;


import java.io.IOException;
import javafx.scene.control.Button;

public class LogIn {
    // UI elements connected with the FXML (Scene Builder)
    @FXML
    private Button loginButton;
    @FXML
    private TextField studentUsername;
    @FXML
    private TextField studentPassword;
    @FXML
    private Label loginMessageLabel;

    // Method triggered when the login button is pressed
    public void loginOnAction() {
        // If fields are empty -> show error message
        if (studentUsername.getText().isBlank() || studentPassword.getText().isBlank()) {
            loginMessageLabel.setText("Your username or password are wrong! Please try again!");
        } else {
            // If fields are not empty → validate login
            validateLogin();
        }
    }

    // Method that validates the login credentials using UserService
    public void validateLogin() {
        UserService userService = new UserService();
        try {
            // Try to find a user with the entered username and password
            User user = userService.findUser(studentUsername.getText(),studentPassword.getText());
            UserData.setCurrentUser(user);

            // Load the "Status Book" page if login is successful
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

    // Redirect from LogIn page to SingUp page
    @FXML
    private Button signupRedirectButton;

    // Method called when the "Sign Up" button is clicked
    @FXML
    private void goToSignUpPage() throws IOException {
        try {
            // Load the Sign Up page
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sign-up.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            loginClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Method to close the current window (Login page)
    public void loginClose() {
        Stage stage = (Stage) signupRedirectButton.getScene().getWindow();
        stage.close();
    }
}