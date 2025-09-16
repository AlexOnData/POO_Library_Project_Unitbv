package com.example.controller;

import com.example.poo_project.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SignUp {

    // UI elements from the FXML interface (Scene Builder)
    @FXML
    private Button backButton;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField university;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button signupButton;
    @FXML
    private Label signupConfirm;

    // Temporary variables to store user input
    String user_id;
    String first_Name;
    String last_Name;
    String univer_sity;
    String user_Name;
    String pass_word;

    // Entry point for creating a new user (called when "Sign Up" button is clicked)
    public void singupNewUser() throws IOException {
        try {
            signUp();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    // Collects data from the UI fields and checks if all are filled
    public void signUp() {
        signupButton.setOnAction(event -> {
            first_Name = firstName.getText();
            last_Name = lastName.getText();
            univer_sity = university.getText();
            user_Name = userName.getText();
            pass_word = password.getText();

            // Validate input: all fields must be filled
            if (first_Name.isEmpty() || last_Name.isEmpty() || univer_sity.isEmpty() || user_Name.isEmpty() || pass_word.isEmpty()) {
                signupConfirm.setText("You have to fill all fields!");
            } else {
                // Insert user data into the database
                insertDataIntoDatabase(pass_word, user_Name, first_Name, last_Name, univer_sity);
                signupConfirm.setText("You have successfully registered!");
            }
        });
    }

    // Inserts user information into the MySQL database
    private void insertDataIntoDatabase(String firstName, String lastName, String university, String userName, String password) {
        // Database connection settings
        String url = "jdbc:mysql://localhost:3306/poo_db";
        String user = "root";
        String pass = "root";

        // SQL query for inserting a new user
        String query = "INSERT INTO user (user_id, password, userName, firstName, lastName, university) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set values for SQL statement
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, pass_word);
            preparedStatement.setString(3, user_Name);
            preparedStatement.setString(4, first_Name);
            preparedStatement.setString(5, last_Name);
            preparedStatement.setString(6, univer_sity);

            int result = preparedStatement.executeUpdate();

            // If user was inserted successfully -> show confirmation alert
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration successful");
                alert.setHeaderText(null);
                alert.setContentText("User registered successfully!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            // If database connection or query fails -> show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred connecting to the database");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    // Redirect from Sign Up page back to Login page
    @FXML
    private void goToLoginPage() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("log-in.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            signupClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Close the Sign Up window
    public void signupClose() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}