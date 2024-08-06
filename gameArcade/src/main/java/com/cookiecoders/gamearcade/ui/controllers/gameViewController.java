package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDao;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDaoImpl;
import com.cookiecoders.gamearcade.games.*;
import com.cookiecoders.gamearcade.users.UserSession;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class gameViewController {
    private UserSession userSession;
    private GameDao gameDao;
    private OwnedGamesDao ownedGamesDao;
    private boolean doubleClickFlag = false;
    private GameManager gameManager;

    @FXML
    private TilePane gamesTilePane;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {
        this.userSession = UserSession.getInstance();
        this.gameDao = new GameDaoImpl();
        this.ownedGamesDao = new OwnedGamesDaoImpl();
        this.gameManager = new GameManager();
        populateOGSP("");
        searchButton.setOnAction(event -> handleSearch());
    }

    private void populateOGSP(String searchQuery) {  // Owned Games Scroll Pane
        List<Map<String, Object>> ownedGames;
        if (userSession.getCurrentUser().getUsertype().equals("admin")) {
            ownedGames = gameDao.getAllGamesSummary();
        } else {
            ownedGames = ownedGamesDao.getOwnedGamesSummary(userSession.getCurrentUser().getId());
        }

        // Filter games based on the search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            ownedGames.removeIf(game -> {
                String gameName = (String) game.get("Title");
                return gameName == null || !gameName.toLowerCase().contains(searchQuery.toLowerCase());
            });
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
            ImageView emptyImageView = createImageView(null, null);
            tilePane.getChildren().add(emptyImageView);
            imageCount++;
        }

        // Add the last TilePane
        mainTilePane.getChildren().add(tilePane);

        gamesTilePane.getChildren().clear();
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

    @FXML
    private void handleMouseClick(MouseEvent event, Integer gameId) {
        if (event.getClickCount() == 2) {
            doubleClickFlag = true;
            launchGame(gameId);
        } else if (event.getClickCount() == 1) {
            PauseTransition pause = new PauseTransition(Duration.millis(200));
            pause.setOnFinished(e -> {
                if (!doubleClickFlag) {
                    navigateToGameReview(event, gameId);
                }
                doubleClickFlag = false;
            });
            pause.play();
        }
    }

    private void launchGame(Integer gameId) {
        // Find game by gameId and launch it
        Game game = getGameById(gameId);
        if (game != null) {
            Stage gameStage = new Stage();
            gameStage.setOnCloseRequest(event -> gameManager.stopGame()); //listen to when game window close
            setupGameStage(gameStage, game);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Game not found!");
            alert.showAndWait();
        }
    }

    private Game getGameById(Integer gameId) {
        // This method should return the game instance based on the gameId
        // For demonstration, we'll return a new PongGame or MinesweeperGame based on gameId
        // You should implement this to return the correct game instance from your database or list
        return switch (gameId) {
            case 1 -> new PongGame();
            case 2 -> new MinesweeperGame();
            case 3 -> new SnakeGame();
            // Add other games here
            default -> null;
        };
    }

    private void setupGameStage(Stage gameStage, Game game) {
        gameManager.loadGame(game);
        gameManager.startGame();
        long startTime = System.currentTimeMillis();

        StackPane gameRoot = gameManager.getGamePane();
        Scene gameScene = new Scene(gameRoot, 800, 600);

        gameStage.setTitle(game.getClass().getSimpleName());
        gameStage.setScene(gameScene);
        gameStage.show();

        gameStage.setOnCloseRequest(event -> {
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            gameManager.recordTime(duration/1000);

            System.out.println("duration: " + (endTime-startTime));
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updateGame();
                gameManager.renderGame();

            }
        }.start();
    }

    @FXML
    private void navigateToGameReview(MouseEvent event, Integer gameId) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/games/gameReviewView.fxml"));
//            loader.setControllerFactory(param -> new gameReviewViewController(gameId));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to load game info page: " + e.getMessage());
//        }
        Navigation.navigateToGameReviewView(event, gameId);
    }

    @FXML
    private void navigationButtonClicked(ActionEvent event) {
        Navigation.toolbarNavigate(event);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();

        // Clear existing games from TilePane
        gamesTilePane.getChildren().clear();

        // Fetch filtered games
        List<Map<String, Object>> filteredGames = filterGames(searchText);

        // Populate the TilePane with filtered games
        populateGamesTilePane(filteredGames);
    }

    private List<Map<String, Object>> filterGames(String searchText) {
        List<Map<String, Object>> allGames = fetchAllGames(); // Fetch all games from your data source

        // Filter games based on the search text
        return allGames.stream()
                .filter(game -> {
                    String gameName = (String) game.get("Title");
                    return gameName != null && gameName.toLowerCase().contains(searchText);
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> fetchAllGames() {
        // Replace with your actual method to fetch all games
        if (userSession.getCurrentUser().getUsertype().equals("admin")) {
            return gameDao.getAllGamesSummary();
        } else {
            return ownedGamesDao.getOwnedGamesSummary(userSession.getCurrentUser().getId());
        }
    }

    private void populateGamesTilePane(List<Map<String, Object>> games) {
        TilePane mainTilePane = new TilePane();
        mainTilePane.setPrefColumns(1);
        mainTilePane.setVgap(0);

        int imageCount = 0;
        TilePane tilePane = new TilePane();
        tilePane.getStyleClass().add("tile-pane");
        tilePane.setPrefColumns(3);
        tilePane.setVgap(0);

        for (Map<String, Object> game : games) {
            if (imageCount % 3 == 0 && imageCount != 0) {
                mainTilePane.getChildren().add(tilePane);
                tilePane = new TilePane();
                tilePane.setPrefColumns(3);
                tilePane.getStyleClass().add("tile-pane");
                mainTilePane.setVgap(0);
            }

            String imagePath = ConfigManager.getProperty("root_path") +
                    ConfigManager.getProperty("game_image_path") +
                    (String) game.get("ImageName");
            Integer gameId = (Integer) game.get("GameID");
            ImageView imageView = createImageView(imagePath, gameId);
            tilePane.getChildren().add(imageView);
            imageCount++;
        }

        while (imageCount % 3 != 0) {
            ImageView emptyImageView = createImageView(null, null);
            tilePane.getChildren().add(emptyImageView);
            imageCount++;
        }

        mainTilePane.getChildren().add(tilePane);
        gamesTilePane.getChildren().add(mainTilePane);
    }
}
