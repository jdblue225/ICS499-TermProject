/**
 * This loginMenuController class handles the login
 * and profile creation functionality via the fxml
 * UI file loginView.
 *
 * @author Cookie Coders
 */

package com.cookiecoders.gamearcade;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class loginMenuController {
    private SQLConnection conn = SQLConnection.getInstance();
    private Logger logger = Logger.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createUserButton;

    @FXML
    private Label errorMessage;


    @FXML
    public void initialize() {
        // Add key press listener to usernameField and passwordField
        usernameField.setOnKeyPressed(event -> handleKeyPress(event, loginButton));
        passwordField.setOnKeyPressed(event -> handleKeyPress(event, loginButton));
        createUserButton.setOnKeyPressed(event -> handleKeyPress(event, createUserButton));
    }

    private void handleKeyPress(KeyEvent event, Button button) {
        if (event.getCode() == KeyCode.ENTER) {
            button.fire();
        }
    }


    /**
     * This method handles action from the login button.
     * It retrieves username and password from the UI, then
     * compares the hashed value of the password with what
     * is stored in the backend SQL database
     */
    @FXML
    private void login() {
        String username = usernameField.getText();
        String hashedPassFromServer = conn.getPasswordForUserID(username);
        String password = hashString(passwordField.getText());
        if (password.equals(hashedPassFromServer)){
            logger.log(Logger.LogLevel.INFO, username.toString() + " login successful.");
            loadProfilePage();
        } else {
            errorMessage.setText("Invalid Credentials");
            logger.log(Logger.LogLevel.WARNING, username.toString() + " login failed.");
        }
    }

    /**
     * This method handles loading the Profile View after login.
     */
    private void loadProfilePage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profileView.fxml"));
            Parent profileRoot = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(profileRoot);
            scene.getStylesheets().add(getClass().getResource("profileView.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Logger.LogLevel.ERROR, "Failed to load profile page: " + e.getMessage());
        }
    }

    /**
     * This method handles creating a new account if one does not exist.
     * Todo
     */
    @FXML
    private void newAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountView.fxml"));
            Parent profileRoot = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(profileRoot);
            scene.getStylesheets().add(getClass().getResource("profileView.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Logger.LogLevel.ERROR, "Failed to load profile page: " + e.getMessage());
        }
    }

    /**
     * This function takes a string and hashes it
     * via the SHA-256 algorithm.
     * @param stringToHash
     * @return hashedString
     */
    private String hashString(String stringToHash){
        String hashedString ="";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(stringToHash.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            hashedString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedString;
    }
}