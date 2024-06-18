package com.cookiecoders.gamearcade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class storeViewController {
    @FXML
    private void navigateToStoreView(ActionEvent event) {
        navigateToView("storeView.fxml", "storeView.css", event);
    }
    @FXML
    private void navigateToGameView(ActionEvent event) {
        navigateToView("gameView.fxml", "gameView.css", event);
    }
    @FXML
    private void navigateToCommunityView(ActionEvent event) {
        navigateToView("communityView.fxml", "communityView.css", event);
    }
    @FXML
    private void navigateToProfileView(ActionEvent event) {
        navigateToView("profileView.fxml", "profileView.css", event);
    }
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
