/**
 * This profileViewController class is the controller
 * for profileView.fxml and handles the UI functionality
 * for this page
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class profileViewController {
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the store button.
     * @param event
     */
    @FXML
    private void navigateToStoreView(ActionEvent event) {
        navigateToView("storeView.fxml", "storeView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the game button.
     * @param event
     */
    @FXML
    private void navigateToGameView(ActionEvent event) {
        navigateToView("gameView.fxml", "gameView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the Community button.
     * @param event
     */
    @FXML
    private void navigateToCommunityView(ActionEvent event) {
        navigateToView("communityView.fxml", "communityView.css", event);
    }
    /**
     * This method handles the menu bar navigational functionality
     * and is called upon clicking on the profile button.
     * @param event
     */
    @FXML
    private void navigateToProfileView(ActionEvent event) {
        navigateToView("profileView.fxml", "profileView.css", event);
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
