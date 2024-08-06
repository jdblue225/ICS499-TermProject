/**
 * This profileViewController class is the controller
 * for profileView.fxml and handles the UI functionality
 * for this page
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class profileViewController {
    @FXML
    private VBox leaderboardContainer;

    @FXML
    public void initialize(){
//        loadLeaderboard();
    }

    private void loadLeaderboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cookiecoders/gamearcade/ui/leaderboard/leaderboardView.fxml"));
            VBox leaderboardView = loader.load();
            leaderboardContainer.getChildren().add(leaderboardView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void navigationButtonClicked(ActionEvent event){
       Navigation.toolbarNavigate(event);
    }

}
