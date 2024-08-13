package com.cookiecoders.gamearcade.ui.controllers;

import com.cookiecoders.gamearcade.database.dao.GameDao;
import com.cookiecoders.gamearcade.database.dao.GameDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void initialize() {
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

    public static class LeaderboardEntry {
        private String username;
        private String gameTitle;
        private Integer playTime;

        public LeaderboardEntry(String username, String gameTitle, Integer playTime) {
            this.username = username;
            this.gameTitle = gameTitle;
            this.playTime = playTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
    private void navigationButtonClicked(ActionEvent event){
        Navigation.toolbarNavigate(event);
    }
}
