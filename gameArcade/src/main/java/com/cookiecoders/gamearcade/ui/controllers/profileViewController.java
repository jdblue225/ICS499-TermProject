/**
 * This profileViewController class is the controller
 * for profileView.fxml and handles the UI functionality
 * for this page
 *
 * @author Cookie Coders
 */
package com.cookiecoders.gamearcade.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.cookiecoders.gamearcade.database.models.User;
import com.cookiecoders.gamearcade.users.UserSession;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class profileViewController {
    @FXML
    private VBox leaderboardContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profileImageView;



    @FXML
    private void initialize() {
        // Load the current user's data from UserSession
        User currentUser = UserSession.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Load and set the username
            usernameLabel.setText(currentUser.getUsername());

            // Load and set the profile image
            String imagePath = currentUser.getFullImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    profileImageView.setImage(image);
                }
            }
        }
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
