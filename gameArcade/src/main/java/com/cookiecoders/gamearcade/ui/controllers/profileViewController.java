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

    //TODO Build profile



    @FXML
    private void navigationButtonClicked(ActionEvent event){
       Navigation.toolbarNavigate(event);
    }

}
