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
import javafx.scene.control.TextField;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.database.dao.UserDao;
import com.cookiecoders.gamearcade.database.dao.UserDaoImpl;



public class profileDetailsViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private void initialize() {
        // Load user data
        User currentUser = UserSession.getInstance().getCurrentUser();
        usernameField.setText(currentUser.getUsername());
        firstNameField.setText(currentUser.getFirstname());
        lastNameField.setText(currentUser.getLastname());
        emailField.setText(currentUser.getEmail());
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

}

// TODO: Implement functionality to upload and display profile pictures.
//       - Add a button for selecting an image file.
//       - Store the selected image path or binary data in the database.
//       - Update the ImageView in the FXML to display the selected profile picture.

// TODO: Implement cancel button functionality in profileDetailsView.
//       - Add event handling for the Cancel button to reset any unsaved changes.
//       - Optionally, navigate back to the previous screen without saving changes.

