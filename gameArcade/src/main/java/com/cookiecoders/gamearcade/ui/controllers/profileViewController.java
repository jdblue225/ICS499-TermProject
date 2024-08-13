/**
 * This profileViewController class is the controller
 * for profileView.fxml and handles the UI functionality
 * for this page
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDao;
import com.cookiecoders.gamearcade.database.dao.OwnedGamesDaoImpl;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.leaderboard.LeaderboardEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.users.UserSession;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.cookiecoders.gamearcade.config.ConfigManager;

import java.net.URL;


import java.io.File;

import java.util.List;
import java.util.Map;

public class profileViewController {
    private Integer userId;
    private User user;
    private GameDao gameDao;
    private OwnedGamesDao ownedGames;
    @FXML
    public ImageView profileImage;
    @FXML
    public Label userName;
    @FXML
    private Label score;
    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, String> gameTitleColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> playTimeColumn;


    @FXML
    private void initialize() {
        // Load the current user's data from UserSession
        User currentUser = UserSession.getInstance().getCurrentUser();
        ownedGames = new OwnedGamesDaoImpl();
        this.gameDao = new GameDaoImpl();
        this.user = UserSession.getInstance().getCurrentUser();
        this.userId = this.user.getId();
        String accountUsername = UserSession.getInstance().getCurrentUser().getUsername();
        userName.setText(accountUsername);
        String imagePath = ConfigManager.getProperty("root_path") +
                ConfigManager.getProperty("prof_image_path") +
                accountUsername + ".jpg";
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            profileImage.setImage(image);
        }
        score.setText(String.valueOf(ownedGames.getUserPlaytime(userId)));
        /**
         * Coming in from Kevin
         */

        // Setup columns
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        gameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
        playTimeColumn.setCellValueFactory(new PropertyValueFactory<>("playTime"));

        // Load leaderboard data
        loadLeaderboardData();
    }

    private void loadLeaderboardData() {
        List<Map<String, Object>> leaderboardData = gameDao.getLeaderboardData();
        for (Map<String, Object> row : leaderboardData) {
            String username = (String) row.get("Username");
            String gameTitle = (String) row.get("Title");
            Integer playTime = (Integer) row.get("PlayTime");

            leaderboardTable.getItems().add(new LeaderboardEntry(username, gameTitle, playTime));
        }
    }

    @FXML
    private void navigateToProfileDeets(ActionEvent event){
        Navigation.navigateToProfileDetailsView(event);
    }

    @FXML
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }


}
