package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.games.Game;
import com.cookiecoders.gamearcade.games.PongGame;
import com.cookiecoders.gamearcade.games.MinesweeperGame;
import com.cookiecoders.gamearcade.games.GameManager;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class gameViewController {
    private UserSession userSession;
    private GameDao gameDao;
    private boolean doubleClickFlag = false;
    private GameManager gameManager;

    @FXML
    private TilePane gamesTilePane;

    @FXML
    private void initialize() {
        this.userSession = UserSession.getInstance();
        this.gameDao = new GameDaoImpl();
        this.gameManager = new GameManager();
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

            String imagePath = ConfigManager.getProperty("root_path") +
                    ConfigManager.getProperty("game_image_path") +
                    (String) game.get("ImageName");
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

        imageView.setOnMouseClicked(event -> handleMouseClick(event, gameId));


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

    private void handleMouseClick(MouseEvent event, Integer gameId) {
        if (event.getClickCount() == 2) {
            doubleClickFlag = true;
            launchGame(gameId);
        } else if (event.getClickCount() == 1) {
            PauseTransition pause = new PauseTransition(Duration.millis(200));
            pause.setOnFinished(e -> {
                if (!doubleClickFlag) {
                    navigateToGameInfo(gameId);
                }
                doubleClickFlag = false;
            });
            pause.play();
        }
    }

    private void launchGame(Integer gameId){
        // Find game by gameId and launch it
        Game game = getGameById(gameId);
        if (game != null) {
            Stage gameStage = new Stage();
            setupGameStage(gameStage, game);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Game not fount!");
            alert.showAndWait();
        }
    }

    private Game getGameById(Integer gameId) {
        // This method should return the game instance based on the gameId
        // For demonstration, we'll return a new PongGame or MinesweeperGame based on gameId
        // You should implement this to return the correct game instance from your database or list
        switch (gameId) {
            case 1:
                return new PongGame();
            case 2:
                return new MinesweeperGame();
            // Add other games here
            default:
                return null;
        }
    }

    private void setupGameStage(Stage gameStage, Game game) {
        gameManager.loadGame(game);
        gameManager.startGame();

        StackPane gameRoot = gameManager.getGamePane();
        Scene gameScene = new Scene(gameRoot, 800, 600);

        gameStage.setTitle(game.getClass().getSimpleName());
        gameStage.setScene(gameScene);
        gameStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updateGame();
                gameManager.renderGame();
            }
        }.start();
    }

    private void navigateToGameInfo(Integer gameId){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/games/GameInfoView.fxml"));
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

    @FXML
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }
}
