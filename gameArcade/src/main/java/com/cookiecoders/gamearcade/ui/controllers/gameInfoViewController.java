package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class gameInfoViewController {
    private Integer gameId;


    public gameInfoViewController(Integer gameId){
        this.gameId = gameId;
    }

    @FXML
    private void initialize(){

    }












    @FXML
    private void closeWindow(ActionEvent event) {
        // Get the source of the event (the button) and close the window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the store button.
     * @param event
     */
    @FXML
    private void navigateToStoreView(ActionEvent event) {
        navigateToView("/com/cookiecoders/gamearcade/ui/store/storeView.fxml", "/com/cookiecoders/gamearcade/ui/store/storeView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the game button.
     * @param event
     */
    @FXML
    private void navigateToGameView(ActionEvent event) {
        navigateToView("/com/cookiecoders/gamearcade/ui/games/gameView.fxml", "/com/cookiecoders/gamearcade/ui/games/gameView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the Community button.
     * @param event
     */
    @FXML
    private void navigateToCommunityView(ActionEvent event) {
        navigateToView("/com/cookiecoders/gamearcade/ui/forum/forumView.fxml", "/com/cookiecoders/gamearcade/ui/forum/forumView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the profile button.
     * @param event
     */
    @FXML
    private void navigateToProfileView(ActionEvent event) {
        navigateToView("/com/cookiecoders/gamearcade/ui/profile/profileView.fxml", "/com/cookiecoders/gamearcade/ui/profile/profileView.css", event);
    }

    /**
     * This method is called whenever a button from the navigational
     * bar is clicked. It then loads the corresponding fxml/css file pair.
     * @param fxmlFile
     * @param cssFile
     * @param event
     */
    private void navigateToView(String fxmlFile, String cssFile, ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load " + fxmlFile + ": " + e.getMessage());
        }
    }
}
