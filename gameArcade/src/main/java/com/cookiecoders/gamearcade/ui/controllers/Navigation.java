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
//              TODO standardize views by collating to one document Master.css
//                cssFile = "/com/cookiecoders/gamearcade/ui/CSS/Master.css";
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
    public static void navigateToGameBuyView(Integer gameId){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/store/gameBuyView.fxml"));
            fxmlLoader.setControllerFactory(param -> new gameBuyViewController(gameId));  // Changed to match the class name convention
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            String css = Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/store/gameBuy.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load game Buy page: " + e.getMessage());
        }
    }

    public static void navigateToGameInfoView(Integer gameId){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/games/GameInfoView.fxml"));
            fxmlLoader.setControllerFactory(param -> new gameBuyViewController(gameId));  // Changed to match the class name convention
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            String css = Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/store/gameInfo.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load game info page: " + e.getMessage());
        }
    }

    public static void  navigateToCreateProfileView(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/login/createAccountView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/login/createAccountView.css").toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load create account page: " + e.getMessage());
        }
    }
}
