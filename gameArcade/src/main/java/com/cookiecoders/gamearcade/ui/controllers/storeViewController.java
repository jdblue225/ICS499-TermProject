/**
 * This storeViewController class is the controller
 * for storeView.fxml and handles the UI functionality
 * for this page.
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class storeViewController {

    //TODO Copy gameViewController logic to build scrollpane of games not owned


    @FXML
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }
}
