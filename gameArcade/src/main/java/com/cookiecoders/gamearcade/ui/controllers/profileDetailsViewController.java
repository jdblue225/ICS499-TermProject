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


public class profileDetailsViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    /**
     * Initializes the profile details view with current user data.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Handles the save action for updating the profile details.
     * @param event The action event triggered by the save button.
     */
    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();

        // TODO: Save profile details to the database or backend.
    }
}
