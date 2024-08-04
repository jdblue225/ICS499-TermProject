package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDao;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.database.models.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.util.List;
import java.util.Map;

/*
    Rating controls documentation:
    https://github.com/controlsfx/controlsfx
    https://coderscratchpad.com/javafx-controlsfx-rating-control/
 */


public class gameReviewViewController {

    private Integer gameId;
    private UserSession userSession;
    private GameDao gameDao;
    private OwnedGamesDao ownedGamesDao;
    private Game game;

    @FXML
    private Button submitButton;
    @FXML
    private Label gameName;
    @FXML
    private Label releaseDate;
    @FXML
    private Rating starRating;
    @FXML
    private TextArea reviewText;
    @FXML
    private ImageView gameImage;
    @FXML
    private TextArea gameDescription;
    @FXML
    public Label paneTitle;


    public gameReviewViewController(Integer gameId){
        this.gameId = gameId;
    }

    @FXML
    private void initialize(){
        this.userSession = UserSession.getInstance();
        this.gameDao = new GameDaoImpl();
        this.ownedGamesDao = new OwnedGamesDaoImpl();
        this.game = gameDao.getGameById(gameId);

        if (this.game != null) {
            paneTitle.setText(game.getTitle() + " Info");
            gameName.setText(game.getTitle());
            releaseDate.setText(String.valueOf(game.getReleaseDate()));
            gameDescription.setText(game.getDescription());
            String imagePath = game.getImagePath();
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            gameImage.setImage(image);
            Map<Double, String> gameReview = ownedGamesDao.getOwnedGameReview(this.userSession.getCurrentUser().getId(), this.gameId);
            if (gameReview != null) {
                for (Map.Entry<Double, String> entry : gameReview.entrySet()) {
                    Double rating = entry.getKey();
                    if (rating != null){
                        starRating.setRating(rating);
                    }
                    String review = entry.getValue();
                    if (review != null){
                        reviewText.setText(review);
                    }
                }
            }
        } else {
            paneTitle.setText("Game Info");
            gameName.setText("Game not found");
            releaseDate.setText("");
            gameDescription.setText("");
            gameImage.setImage(null);
            starRating.setRating(0);
        }
    }

    @FXML
    private void navigateToGameView(ActionEvent event){
        Navigation.navigateToGameView(event);
    }

    @FXML
    private void submitReview(ActionEvent event){
        Integer userID = this.userSession.getCurrentUser().getId();
        Integer gameID = this.gameId;
        String review = reviewText.getText();
        Double rating = starRating.getRating();
        this.ownedGamesDao.updateGameReview(userID,gameID,rating,review);
        submitButton.setDisable(true);
    }

}
