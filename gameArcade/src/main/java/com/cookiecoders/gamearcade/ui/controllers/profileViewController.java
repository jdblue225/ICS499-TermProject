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


import java.io.File;

import java.util.List;
import java.util.Map;

public class profileViewController {
    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;

    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> gameTitleColumn;

    @FXML
    private TableColumn<LeaderboardEntry, Integer> playTimeColumn;

    private GameDao gameDao;

    public profileViewController() {
        this.gameDao = new GameDaoImpl();
    }

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profileImageView;



    @FXML
    private void initialize() {
        // Load the current user's data from UserSession
        User currentUser = UserSession.getInstance().getCurrentUser();

//        if (currentUser != null) {
//            // Load and set the username
//            usernameLabel.setText(currentUser.getUsername());
//
//            // Load and set the profile image
//            String imagePath = currentUser.getFullImagePath();
//
//            if (imagePath != null && !imagePath.isEmpty()) {
//                File imageFile = new File(imagePath);
//                if (imageFile.exists()) {
//                    Image image = new Image(imageFile.toURI().toString());
//                    profileImageView.setImage(image);
//                }
//            }
//        }
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
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }

}
