/**
 * This loginMenuController class handles the login
 * and profile creation functionality via the fxml
 * UI file loginView.
 *
 * @author Cookie Coders
 */

package com.cookiecoders.gamearcade.ui.controllers;


import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import com.cookiecoders.gamearcade.SQLConnection;
import com.cookiecoders.gamearcade.database.dao.UserDao;
import com.cookiecoders.gamearcade.database.dao.UserDaoImpl;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.security.SecurityManager;
import com.cookiecoders.gamearcade.util.Logger;
import com.cookiecoders.gamearcade.users.*;
import javafx.event.ActionEvent;
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
//    private SQLConnection conn = SQLConnection.getInstance();
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
        UserDao userDao = new UserDaoImpl();        //instatiate User data access object
        User user = userDao.getUserByUsername(username); //populate user object from server
        String enteredPasswordHashed = SecurityManager.hashString(passwordField.getText());
        String passFromServer= user.getPassword();
        if (SecurityManager.authenticate(enteredPasswordHashed,passFromServer)){
            UserSession currentSession = UserSession.getInstance();
            currentSession.setCurrentUser(user);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/profile/profileView.fxml"));
            Parent profileRoot = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(profileRoot);
            scene.getStylesheets().add(getClass().getResource("/com/cookiecoders/gamearcade/ui/profile/profileView.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Logger.LogLevel.ERROR, "Failed to load profile page: " + e.getMessage());
        }
    }

    /**
     * This method handles creating a new account if one does not exist.
     */
    @FXML
    private void newAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/login/createAccountView.fxml"));
            Parent profileRoot = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(profileRoot);
            scene.getStylesheets().add(getClass().getResource("/com/cookiecoders/gamearcade/ui/login/createAccountView.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Logger.LogLevel.ERROR, "Failed to load profile page: " + e.getMessage());
        }
    }
    @FXML
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }

}