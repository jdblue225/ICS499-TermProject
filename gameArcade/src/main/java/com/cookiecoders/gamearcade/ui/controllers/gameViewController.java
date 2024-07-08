package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
    private GameDao gameDao;

    @FXML
    private TilePane gamesTilePane;
    //TODO

    @FXML
    private void initialize() {
        this.userSession = UserSession.getInstance();
        this.gameDao = new GameDaoImpl();
        populateOGSP();

    }

    private void populateOGSP(){  // Owned Games Scroll Pane
        List<Map<String, Object>> ownedGames;
        if (userSession.getCurrentUser().getUsertype().equals("admin")){
            ownedGames = gameDao.getAllGamesSummary();
        } else{
            ownedGames = gameDao.getOwnedGamesSummary(userSession.getCurrentUser().getId());
        }

        TilePane mainTilePane = new TilePane();
        mainTilePane.setPrefColumns(1); // Ensuring one column to stack TilePanes vertically
        mainTilePane.setVgap(0); // Vertical gap between tiles

        int imageCount = 0;
        TilePane tilePane = new TilePane();
        tilePane.getStyleClass().add("tile-pane");
        tilePane.setPrefColumns(3); // Set the number of columns to 3 for each TilePane
        tilePane.setVgap(0); // Vertical gap between tiles


        for (Map<String, Object> game : ownedGames) {
            if (imageCount % 3 == 0 && imageCount != 0) {
                mainTilePane.getChildren().add(tilePane);
                tilePane = new TilePane();
                tilePane.setPrefColumns(3);
                tilePane.getStyleClass().add("tile-pane");
                mainTilePane.setVgap(0); // Vertical gap between tiles
            }

            String imagePath = "/com/cookiecoders/gamearcade" + (String) game.get("ImagePath");
            Integer gameId = (Integer) game.get("GameID");
            ImageView imageView = createImageView(imagePath, gameId);
            tilePane.getChildren().add(imageView);
            imageCount++;
        }

        // Add empty images if the last TilePane has fewer than 3 images
        while (imageCount % 3 != 0) {
            ImageView emptyImageView = createImageView(null,null);
            tilePane.getChildren().add(emptyImageView);
            imageCount++;
        }


        // Add the last TilePane
        mainTilePane.getChildren().add(tilePane);

        // Add the mainTilePane to the ScrollPane
        gamesTilePane.getChildren().add(mainTilePane);
    }

    private ImageView createImageView(String imagePath, Integer gameId) {
        Image image = null;
        ImageView imageView;

        try {
            if (imagePath != null) {
                image = new Image(getClass().getResourceAsStream(imagePath));
            }

            // Check if the image was loaded successfully
            if (image == null || image.isError()) {
                throw new IllegalArgumentException("Image not found");
            }

            imageView = new ImageView(image);
        } catch (Exception e) {
            // If an error occurs (image not found or any other issue), create an empty ImageView
            imageView = new ImageView();
        }

        imageView.getStyleClass().add("image-view");
        cropImageView(imageView, 100, 100); // Crop to fit the width and height
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPickOnBounds(false);
//        imageView.setPreserveRatio(true);


        // Set click event to navigate to game info
        imageView.setOnMouseClicked(event -> navigateToGameInfo(gameId));


        TilePane.setMargin(imageView, new javafx.geometry.Insets(15));
        return imageView;
    }

    private void cropImageView(ImageView imageView, double fitWidth, double fitHeight) {
        Image image = imageView.getImage();
        if (image != null) {
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            double aspectRatio = imageWidth / imageHeight;
            double fitRatio = fitWidth / fitHeight;

            double cropWidth;
            double cropHeight;

            if (aspectRatio > fitRatio) {
                cropWidth = imageHeight * fitRatio;
                cropHeight = imageHeight;
            } else {
                cropWidth = imageWidth;
                cropHeight = imageWidth / fitRatio;
            }

            double x = (imageWidth - cropWidth) / 2;
            double y = (imageHeight - cropHeight) / 2;

            Rectangle2D viewport = new Rectangle2D(x, y, cropWidth, cropHeight);
            imageView.setViewport(viewport);
        }
    }




    private void navigateToGameInfo(Integer gameId){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/GameInfoView.fxml"));

            // Use a custom controller factory to pass the gameId to the controller
            loader.setControllerFactory(param -> new gameInfoViewController(gameId));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load game info page: " + e.getMessage());
        }
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
