package com.cookiecoders.gamearcade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class loginMenuController {
    private SQLConnection conn = SQLConnection.getInstance();
    private Logger logger = Logger.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() {
        String username = usernameField.getText();
        String hashedPassFromServer = conn.getPasswordForUserID(username);
        String password = hashString(passwordField.getText());
        if (password.equals(hashedPassFromServer)){
            logger.log(Logger.LogLevel.INFO, username.toString() + " login successful.");
            //Navigate to next page
        } else {
            logger.log(Logger.LogLevel.WARNING, username.toString() + " login failed.");
        }
    }

    @FXML
    private void newAccount() {
        // handle creating a new account via SQL class
    }

    private String hashString(String stringToHash){
        String hashedString ="";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(stringToHash.getBytes());

            // Convert the byte array to a hexadecimal string
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
