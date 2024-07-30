/**
 * This profileViewController class is the controller
 * for profileView.fxml and handles the UI functionality
 * for this page
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class profileViewController {

    private void initialize() {
        // TODO: Add code to initialize the profile view, e.g., load user data.
    }


    @FXML
    private void navigationButtonClicked(ActionEvent event){
       Navigation.toolbarNavigate(event);
    }

}
