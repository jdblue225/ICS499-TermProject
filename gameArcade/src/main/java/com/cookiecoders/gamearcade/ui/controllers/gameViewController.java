package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class gameViewController {
    private UserSession userSession;

    @FXML
    private TilePane gamesTilePane;
    //TODO

    private void initialize() {
        this.userSession = UserSession.getInstance();
        GameDao gameDao = new GameDaoImpl();
        List<Map<String, Object>> ownedGames = gameDao.getOwnedGamesSummary(userSession.getCurrentUser().getId());
        for (Map<String, Object> game : ownedGames) {
            String imagePath = (String) game.get("ImagePath");
            ImageView imageView = createImageView(imagePath);
            gamesTilePane.getChildren().add(imageView);
        }
    }
    private ImageView createImageView(String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        TilePane.setMargin(imageView, new javafx.geometry.Insets(15));
        return imageView;
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
