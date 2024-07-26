/**
 * This is the main class to the Cookie Coders application
 * developed for ICS 499 Capstone class summer of 2024.
 *
 * This is the main method and is to be initially called
 * in order to start the application.
 *
 * @author Cookie Coders
 *
 *      Brandon Crass
 *      Jeremy Daly
 *      Maddy Imhoff
 *      Kevin Trinh
 *
 */

package com.cookiecoders.gamearcade;

import com.cookiecoders.gamearcade.database.DatabaseManager;
import com.cookiecoders.gamearcade.util.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
    Logger logger = Logger.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        logger.log(Logger.LogLevel.INFO, "Starting Cookie Arcade");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ui/login/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("ui/login/loginView.css").toExternalForm());
        stage.setTitle("Cookie Arcade");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        try {
            if(DatabaseManager.getInstance().verifyConnection()) {
                // Close the database connection
                DatabaseManager.getInstance().connectionClose();
            }

        } finally {
            // Ensure JavaFX platform is exited
            Platform.exit();

            // Force the application to exit
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

