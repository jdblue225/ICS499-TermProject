/**
 * This profileDetailsViewController class is the controller
 * for profileDetailsView.fxml and handles the UI functionality
 * for editing or viewing detailed profile information.
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.database.dao.UserDao;
import com.cookiecoders.gamearcade.database.dao.UserDaoImpl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


public class profileDetailsViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button uploadButton;

    @FXML
    private ImageView profileImage;

    private File imageFile;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // Load user data
        User currentUser = UserSession.getInstance().getCurrentUser();
        usernameLabel.setText(currentUser.getUsername());
        usernameField.setText(currentUser.getUsername());
        firstNameField.setText(currentUser.getFirstname());
        lastNameField.setText(currentUser.getLastname());
        emailField.setText(currentUser.getEmail());

        // Setting up action handlers for the buttons
        uploadButton.setOnAction(this::handleUploadButtonAction);
        saveButton.setOnAction(this::handleSaveButtonAction);
        cancelButton.setOnAction(this::handleCancelButtonAction);

    }


    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();

        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.setUsername(username);
            currentUser.setFirstname(firstName);
            currentUser.setLastname(lastName);
            currentUser.setEmail(email);

            UserDao userDao = new UserDaoImpl();
            userDao.updateUser(currentUser);

            // Reload user from the database
            User updatedUser = userDao.getUserByUsername(currentUser.getUsername());
            UserSession.getInstance().setCurrentUser(updatedUser);

            // Update UI with the refreshed user data
            initialize();
        }
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        try {
            // Create a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Profile Image");
            // Set extension filters (optional)
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            // Open the FileChooser dialog
            Stage stage = (Stage) uploadButton.getScene().getWindow();
            this.imageFile = fileChooser.showOpenDialog(stage);

            if (imageFile != null) {
                // Load the selected image file into the ImageView
                Image image = new Image(imageFile.toURI().toString());
                profileImage.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace(); // This will help identify the exact issue
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        Navigation.navigateToProfileView(event);
//        try {
//            // Load the profile view FXML
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/profile/profileView.fxml"));
//            Parent root = loader.load();
//            Stage stage = (Stage) cancelButton.getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}

// TODO: Implement save functionality