package com.cookiecoders.gamearcade.ui.controllers;

//import com.cookiecoders.gamearcade.SQLConnection;
import com.cookiecoders.gamearcade.database.dao.UserDao;
import com.cookiecoders.gamearcade.database.dao.UserDaoImpl;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.security.SecurityManager;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class createAccountViewController {
//    private SQLConnection conn = SQLConnection.getInstance();
    private Logger logger = Logger.getInstance();
    private UserDao userDao;
    private User user;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button checkButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorMessageUName;

    @FXML
    private Label errorMessageFName;

    @FXML
    private Label errorMessageLName;

    @FXML
    private Label errorMessageEmail;

    @FXML
    private Label errorMessagePass;

    @FXML
    private Label errorMessagePassCheck;

    @FXML
    private void handleCheckButtonAction(ActionEvent event) {
        // Handle Check button action
        if(validateUsername(userNameField.getText())){
            errorMessageUName.setStyle("-fx-text-fill: red;");
            errorMessageUName.setText("Username Unavailable");
        }else{
            errorMessageUName.setStyle("-fx-text-fill: green;");
            errorMessageUName.setText("Username Available");
        }
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        // Validate form fields
        if (validateForm()) {
            System.out.println("User Created!");
            // Proceed with form submission logic
            createUser();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Creation");
            alert.setHeaderText(null);
            alert.setContentText("User Created Successfully!");
            alert.showAndWait();
            navigateLoginView(event);
        }
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        // Handle Upload button action
        System.out.println("Upload button clicked!");
        // You can add file upload logic here
    }

    @FXML
    private void navigateLoginView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/login/loginView.fxml"));
            Parent loginRoot = fxmlLoader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(loginRoot);
            scene.getStylesheets().add(getClass().getResource("/com/cookiecoders/gamearcade/ui/login/loginView.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Logger.LogLevel.CRITICAL, "Failed to load login page: " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        checkButton.setOnAction(this::handleCheckButtonAction);
        submitButton.setOnAction(this::handleSubmitButtonAction);
        uploadButton.setOnAction(this::handleUploadButtonAction);
        cancelButton.setOnAction(this::navigateLoginView);
    }

    private boolean validateForm() {
        boolean isValid = true;

        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (userName.isEmpty()) {
            errorMessageUName.setStyle("-fx-text-fill: red;");
            errorMessageUName.setText("User Name is required.");
            isValid = false;
        } else {
            errorMessageUName.setText("");
        }

        if (firstName.isEmpty()) {
            errorMessageFName.setText("First Name is required.");
            isValid = false;
        } else {
            errorMessageFName.setText("");
        }

        if (lastName.isEmpty()) {
            errorMessageLName.setText("Last Name is required.");
            isValid = false;
        } else {
            errorMessageLName.setText("");
        }

        if (email.isEmpty()) {
            errorMessageEmail.setText("Email is required.");
            isValid = false;
        } else {
            errorMessageEmail.setText("");
        }

        if (password.isEmpty()) {
            errorMessagePass.setText("Password is required.");
            isValid = false;
        } else {
            errorMessagePass.setText("");
        }

        if (confirmPassword.isEmpty()) {
            errorMessagePassCheck.setText("Please confirm your password.");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            errorMessagePassCheck.setText("Passwords do not match.");
            isValid = false;
        } else {
            errorMessagePassCheck.setText("");
        }

        return isValid;
    }

    private boolean validateUsername(String username){
        this.userDao = new UserDaoImpl();
        if (userDao.getUserByUsername(username) == null){
            return false;
        }else{
            return true;
        }
    }

    private boolean createUser(){
        if(this.userDao == null){
            this.userDao = new UserDaoImpl();
        }
        String encrPass = SecurityManager.hashString(passwordField.getText());
        this.user = new User(
                userNameField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                encrPass,
                "user"
        );
        return userDao.insertUser(user);
    }
    @FXML
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }

    // TODO handle profile image import to SQL db
}
