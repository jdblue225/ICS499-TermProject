package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static String uiRoot = ConfigManager.getProperty("root_path") + "ui/";
    //    Game View    //
    private static String GAME_VIEW_FXML = uiRoot + "games/gameView.fxml";
    private static String GAME_VIEW_CSS = uiRoot + "games/gameView.css";
    //    Store View    //
    private static String STORE_VIEW_FXML = uiRoot + "store/storeView.fxml";
    private static String STORE_VIEW_CSS = uiRoot + "store/storeView.css";
    //    Profile View    //
    private static String PROFILE_VIEW_FXML = uiRoot + "profile/profileView.fxml";
    private static String PROFILE_VIEW_CSS = uiRoot + "profile/profileView.css";
    //    Forum View    //
    private static String FORUM_VIEW_FXML = uiRoot + "forum/forumView.fxml";
    private static String FORUM_VIEW_CSS = uiRoot + "forum/forumView.css";
    //    Review View    //
    private static String REVIEW_VIEW_FXML = uiRoot + "games/gameReviewView.fxml";
    private static String REVIEW_VIEW_CSS = uiRoot + "games/gameReview.css";
    //    Buy View    //
    private static String BUY_VIEW_FXML = uiRoot + "store/gameBuyView.fxml";
    private static String BUY_VIEW_CSS = uiRoot + "store/gameBuy.css";
    //    Create Account View    //
    private static String CREATE_ACCOUNT_VIEW_FXML = uiRoot + "login/createAccountView.fxml";
    private static String CREATE_ACCOUNT_VIEW_CSS = uiRoot + "login/createAccountView.css";
    //    Login View    //
    private static String LOGIN_VIEW_FXML = uiRoot + "login/loginView.fxml";
    private static String LOGIN_VIEW_CSS =  uiRoot + "login/loginView.css";




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
                fxmlFile = STORE_VIEW_FXML;
                cssFile = STORE_VIEW_CSS;
                break;
            case "toolbarGameButton":
                fxmlFile = GAME_VIEW_FXML;
                cssFile = GAME_VIEW_CSS;
                break;
            case "toolbarForumButton":
                fxmlFile = FORUM_VIEW_FXML;
                cssFile = FORUM_VIEW_CSS;
                break;
            case "toolbarProfileButton":
                fxmlFile = PROFILE_VIEW_FXML;
                cssFile = PROFILE_VIEW_CSS;
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
    public static void navigateToGameBuyView(MouseEvent event, Integer gameId){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(BUY_VIEW_FXML));
            fxmlLoader.setControllerFactory(param -> new gameBuyViewController(gameId));  // Changed to match the class name convention
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource(BUY_VIEW_CSS).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load " + BUY_VIEW_FXML + ": " + e.getMessage());
        }
    }

    public static void navigateToGameReviewView(MouseEvent event, Integer gameId){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/games/gameReviewView.fxml"));
//            fxmlLoader.setControllerFactory(param -> new gameBuyViewController(gameId));  // Changed to match the class name convention
//            Parent root = fxmlLoader.load();
//            Scene scene = new Scene(root);
//            String css = Navigation.class.getResource("/com/cookiecoders/gamearcade/ui/store/gameReview.css").toExternalForm();
//            scene.getStylesheets().add(css);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load game info page: " + e.getMessage());
//        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(REVIEW_VIEW_FXML));
            fxmlLoader.setControllerFactory(param -> new gameReviewViewController(gameId));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource(REVIEW_VIEW_CSS).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load " + REVIEW_VIEW_FXML + ": " + e.getMessage());
        }
    }

    public static void navigateToCreateProfileView(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(CREATE_ACCOUNT_VIEW_FXML));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource(CREATE_ACCOUNT_VIEW_CSS).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load create account page: "+ CREATE_ACCOUNT_VIEW_FXML + e.getMessage());
        }
    }
    public static void navigateToStoreView(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(STORE_VIEW_FXML));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.class.getResource(STORE_VIEW_CSS).toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load create account page: "+ STORE_VIEW_FXML + e.getMessage());
        }
    }

    public static void navigateToGameView(ActionEvent event){
        String fxmlFile = GAME_VIEW_FXML;
        String cssFile = GAME_VIEW_CSS;
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
