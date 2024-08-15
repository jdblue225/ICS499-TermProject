/**
 * This profileDetailsViewController class is the controller
 * for profileDetailsView.fxml and handles the UI functionality
 * for editing or viewing detailed profile information.
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.util.Utils;
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
import java.net.URL;


public class profileDetailsViewController {
    private User user;
    private String userName;
    private File imageFile;
    private UserDao userDao;

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
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;



    @FXML
    private void initialize() {
        userDao = new UserDaoImpl();
        this.user = UserSession.getInstance().getCurrentUser();
        this.userName = this.user.getUsername();
        // Load user data
        usernameLabel.setText(this.userName);
        usernameField.setText(this.user.getUsername());
        firstNameField.setText(this.user.getFirstname());
        lastNameField.setText(this.user.getLastname());
        emailField.setText(this.user.getEmail());

        // Setting up action handlers for the buttons
        uploadButton.setOnAction(this::handleUploadButtonAction);
        saveButton.setOnAction(this::handleSaveButtonAction);
        cancelButton.setOnAction(this::handleCancelButtonAction);
        loadProfileImage();
    }

    private void loadProfileImage(){
        if(this.user.getImage() != null){
            Image image = Utils.byteArrayToImage(user.getImage());
            profileImage.setImage(image);
        }
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        
        if (this.user != null) {
            byte[] profImageByte = null;
            String imageName = null;
            if (imageFile != null){
                String imageExtension = Utils.getFileExtension(imageFile.toURI().toString());
                imageName = username + "." + imageExtension;
                profImageByte = Utils.imageToByteArray(profileImage.getImage());
            }else{
                profImageByte = this.user.getImage();
            }
            this.user.setUsername(username);
            this.user.setFirstname(firstName);
            this.user.setLastname(lastName);
            this.user.setEmail(email);
            this.user.setImageName(imageName);  // Hardcoded filetype
            this.user.setImage(profImageByte);

            userDao.updateUser(this.user);

            // Reload user from the database
            User updatedUser = userDao.getUserByUsername(this.user.getUsername());
            UserSession.getInstance().setCurrentUser(updatedUser);

//            // Update UI with the refreshed user data
//            initialize();
            Navigation.navigateToProfileView(event);
        }
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        // Set extension filters (optional)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Open the FileChooser dialog
        Stage stage = (Stage) uploadButton.getScene().getWindow(); // Get the current window
        this.imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile != null) {
            // Load the selected image file into the ImageView
            Image image = new Image(imageFile.toURI().toString());
            profileImage.setImage(image);
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        Navigation.navigateToProfileView(event);
    }

}
