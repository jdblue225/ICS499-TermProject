package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    /**
     * This method is called whenever a button from the navigational
     * bar is clicked. It then loads the corresponding fxml/css file pair.
     * @param event The action event that triggered the navigation.
     */
    public static void toolbarNavigate(ActionEvent event) {
        String fxmlFile = "";
        String cssFile = "";

        // Get the button that was clicked
        String buttonId = ((javafx.scene.control.Button) event.getSource()).getId();

        switch (buttonId) {
            case "toolbarStoreButton":
                fxmlFile = "/com/cookiecoders/gamearcade/ui/store/storeView.fxml";
                cssFile = "/com/cookiecoders/gamearcade/ui/store/storeView.css";
                break;
            case "toolbarGameButton":
                fxmlFile = "/com/cookiecoders/gamearcade/ui/games/gameView.fxml";
                cssFile = "/com/cookiecoders/gamearcade/ui/games/gameView.css";
                break;
            case "toolbarForumButton":
                fxmlFile = "/com/cookiecoders/gamearcade/ui/forum/forumView.fxml";
                cssFile = "/com/cookiecoders/gamearcade/ui/forum/forumView.css";
                break;
            case "toolbarProfileButton":
                fxmlFile = "/com/cookiecoders/gamearcade/ui/profile/profileView.fxml";
                cssFile = "/com/cookiecoders/gamearcade/ui/profile/profileView.css";
                break;
            default:
                throw new IllegalArgumentException("Unexpected button ID: " + buttonId);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource(cssFile).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load " + fxmlFile + ": " + e.getMessage());
        }
    }
}
