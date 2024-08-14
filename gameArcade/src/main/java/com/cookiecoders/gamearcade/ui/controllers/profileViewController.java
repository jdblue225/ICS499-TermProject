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
//import com.cookiecoders.gamearcade.leaderboard.LeaderboardEntry;
import com.cookiecoders.gamearcade.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.users.UserSession;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.cookiecoders.gamearcade.config.ConfigManager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
    public Button logoutButton;
    @FXML
    public ImageView profileImage;
    @FXML
    public Label userName;
    @FXML
    private Label score;
    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> userNameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, String> gameTitleColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> playTimeColumn;


    @FXML
    private void initialize() {
        // Load the current user's data from UserSession
//        User currentUser = UserSession.getInstance().getCurrentUser();
        ownedGames = new OwnedGamesDaoImpl();
        this.gameDao = new GameDaoImpl();
        this.user = UserSession.getInstance().getCurrentUser();
        this.userId = this.user.getId();
        userName.setText(user.getUsername());
        loadProfileImage();
        score.setText(String.valueOf(ownedGames.getUserPlaytime(userId)));
        /**
         * Coming in from Kevin
         */

        // Setup columns
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        gameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("gameTitle"));
        playTimeColumn.setCellValueFactory(new PropertyValueFactory<>("playTime"));

        // Load leaderboard data
        loadLeaderboardData();
    }
    private void loadProfileImage(){
        if(this.user.getImage() != null){
            Image image = Utils.byteArrayToImage(user.getImage());
            profileImage.setImage(image);
        }
    }

    private void loadLeaderboardData() {
        List<Map<String, Object>> leaderboardData = gameDao.getLeaderboardData();
        for (Map<String, Object> row : leaderboardData) {
            String userName = (String) row.get("UserName");
            String gameTitle = (String) row.get("Title");
            Integer playTime = (Integer) row.get("PlayTime");

            leaderboardTable.getItems().add(new LeaderboardEntry(userName, gameTitle, playTime));
        }
    }
    public static class LeaderboardEntry {
        private String userName;
        private String gameTitle;
        private Integer playTime;

        public LeaderboardEntry(String userName, String gameTitle, Integer playTime) {
            this.userName = userName;
            this.gameTitle = gameTitle;
            this.playTime = playTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGameTitle() {
            return gameTitle;
        }

        public void setGameTitle(String gameTitle) {
            this.gameTitle = gameTitle;
        }

        public Integer getPlayTime() {
            return playTime;
        }

        public void setPlayTime(Integer playTime) {
            this.playTime = playTime;
        }
    }
    @FXML
    private void logout(ActionEvent event){
        UserSession.getInstance().clearSession();
        Navigation.navigateToLoginView(event);
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
