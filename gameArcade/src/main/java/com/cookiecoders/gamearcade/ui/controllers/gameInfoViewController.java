package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.users.UserSession;
import com.cookiecoders.gamearcade.database.models.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/*
    Rating controls documentation:
    https://github.com/controlsfx/controlsfx
    https://coderscratchpad.com/javafx-controlsfx-rating-control/
 */


public class gameInfoViewController {

    private Integer gameId;
    private UserSession userSession;
    private GameDao gameDao;
    private Game game;

    @FXML
    private Label gameName;
    @FXML
    private Label releaseDate;
    @FXML
    private Rating starRating;
    @FXML
    private ImageView gameImage;
    @FXML
    private TextArea gameDescription;
    @FXML
    public Label paneTitle;


    public gameInfoViewController(Integer gameId){
        this.gameId = gameId;
    }

    @FXML
    private void initialize(){
        this.userSession = UserSession.getInstance();
        this.gameDao = new GameDaoImpl();
        this.game = gameDao.getGameById(gameId);
        if (this.game != null) {
            paneTitle.setText(game.getTitle() + " Info");
            gameName.setText(game.getTitle());
            releaseDate.setText(String.valueOf(game.getReleaseDate()));
            gameDescription.setText(game.getDescription());
            String imagePath = game.getImagePath();
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            gameImage.setImage(image);
            starRating.setRating(this.game.getAverageRating());
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
    private void closeWindow(ActionEvent event) {
        // Get the source of the event (the button) and close the window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void navigateToRating(ActionEvent event){
        //Navigation logic
    }
}
